package com.example.piyo.presentation.infoanak

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.piyo.R
import com.example.piyo.presentation.camera.CameraCaptureScreen
import com.example.piyo.ui.theme.BlueMain
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream

enum class PhotoType {
    PROFILE, BABY
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun InfoAnakScreen(
    navController: NavController,
    childId: String? = null,
    viewModel: ChildInfoViewModel = koinViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Camera permission state
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    // Dialog and camera states
    var showPhotoPickerDialog by remember { mutableStateOf(false) }
    var showCameraForProfile by remember { mutableStateOf(false) }
    var showCameraForBaby by remember { mutableStateOf(false) }
    var photoTypeToCapture by remember { mutableStateOf<PhotoType?>(null) }
    var pendingCameraAction by remember { mutableStateOf<PhotoType?>(null) }

    // Handle camera permission result
    LaunchedEffect(cameraPermissionState.status.isGranted) {
        if (cameraPermissionState.status.isGranted && pendingCameraAction != null) {
            when (pendingCameraAction) {
                PhotoType.PROFILE -> showCameraForProfile = true
                PhotoType.BABY -> showCameraForBaby = true
                else -> {}
            }
            pendingCameraAction = null
        }
    }

    // Function to request camera with permission check
    fun requestCameraWithPermission(photoType: PhotoType) {
        if (cameraPermissionState.status.isGranted) {
            when (photoType) {
                PhotoType.PROFILE -> showCameraForProfile = true
                PhotoType.BABY -> showCameraForBaby = true
            }
        } else {
            pendingCameraAction = photoType
            cameraPermissionState.launchPermissionRequest()
        }
    }

    // Image picker launchers
    val profilePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onProfilePhotoSelected(it)
            // Upload immediately if we have a childId
            val tempId = childId ?: "temp_${System.currentTimeMillis()}"
            val photoData = uriToByteArray(context, it)
            if (photoData != null) {
                viewModel.uploadProfilePhoto(photoData, tempId)
            }
        }
    }

    val babyPhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onBabyPhotoSelected(it)
            // Upload immediately if we have a childId
            val tempId = childId ?: "temp_${System.currentTimeMillis()}"
            val photoData = uriToByteArray(context, it)
            if (photoData != null) {
                viewModel.uploadBabyPhoto(photoData, tempId)
            }
        }
    }

    // Handle camera captured photo
    fun handleCapturedPhoto(uri: Uri, isProfile: Boolean) {
        val tempId = childId ?: "temp_${System.currentTimeMillis()}"
        val photoData = uriToByteArray(context, uri)
        if (photoData != null) {
            if (isProfile) {
                viewModel.onProfilePhotoSelected(uri)
                viewModel.uploadProfilePhoto(photoData, tempId)
            } else {
                viewModel.onBabyPhotoSelected(uri)
                viewModel.uploadBabyPhoto(photoData, tempId)
            }
        }
    }

    // Load child data if editing
    LaunchedEffect(childId) {
        viewModel.loadChild(childId)
    }

    // Handle success
    LaunchedEffect(state.success) {
        if (state.success) {
            snackbarHostState.showSnackbar("Informasi anak berhasil disimpan")
            viewModel.resetSuccess()
            // Navigate to MainRoute instead of popping back
            navController.navigate(com.example.piyo.presentation.navigation.MainRoute) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    // Handle error
    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Informasi Anak", fontWeight = FontWeight.Bold, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White,
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
                    .imePadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = { viewModel.saveChild() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BlueMain),
                    enabled = !state.isSaving
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text("Simpan", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            item {
                Spacer(Modifier.height(8.dp))
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(132.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEFF6FC))
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(4.dp)
                                .clickable {
                                    photoTypeToCapture = PhotoType.PROFILE
                                    showPhotoPickerDialog = true
                                }
                        ) {
                            if (state.profilePhotoUri != null || state.profilePhotoUrl.isNotBlank()) {
                                AsyncImage(
                                    model = state.profilePhotoUri ?: state.profilePhotoUrl,
                                    contentDescription = "Foto Profil",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_child_placeholder),
                                    contentDescription = "Foto Si Kecil",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                        .background(Color.White)
                                )
                            }

                            if (state.isUploadingProfile) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.5f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = Color.White)
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .borderStroke(2.dp, BlueMain, CircleShape)
                            .clickable { profilePhotoLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit", tint = BlueMain)
                    }
                }
            }

            item {
                LabeledField(label = "Nama Lengkap") {
                    OutlinedInput(
                        value = state.fullName,
                        onValueChange = { viewModel.onNameChange(it) }
                    )
                }
            }

            item {
                LabeledField(label = "Tanggal Lahir") {
                    OutlinedInput(
                        value = state.birthDate,
                        onValueChange = { viewModel.onBirthDateChange(it) },
                        placeholder = "YYYY-MM-DD"
                    )
                }
            }

            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Jenis Kelamin", fontSize = 16.sp, color = Color.Black)
                    Spacer(Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        GenderOption(
                            text = "Perempuan",
                            selected = state.gender == "female",
                            onClick = { viewModel.onGenderChange("female") }
                        )
                        GenderOption(
                            text = "Laki-Laki",
                            selected = state.gender == "male",
                            onClick = { viewModel.onGenderChange("male") }
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Data Kelahiran",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Foto Si Kecil", fontSize = 16.sp, color = Color.Black)
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .dashedBorder(2.dp, BlueMain, 32.dp, on = 14f, off = 10f)
                            .clip(RoundedCornerShape(32.dp))
                            .clickable {
                                photoTypeToCapture = PhotoType.BABY
                                showPhotoPickerDialog = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (state.babyPhotoUri != null || state.babyPhotoUrl.isNotBlank()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (state.isUploadingBaby) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = BlueMain
                                    )
                                }
                                Text(
                                    text = if (state.isUploadingBaby) "Mengunggah..." else "Foto berhasil dipilih ✓",
                                    color = BlueMain,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = BlueMain)
                                Spacer(Modifier.width(6.dp))
                                Text(text = "+ Unggah Foto", color = BlueMain, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            item {
                LabeledField(label = "Berat Badan Saat Lahir (Kg)") {
                    OutlinedInput(
                        value = state.birthWeight,
                        onValueChange = { viewModel.onBirthWeightChange(it) },
                        keyboardType = KeyboardType.Decimal
                    )
                }
            }

            item {
                LabeledField(label = "Riwayat Diagnosis") {
                    OutlinedInput(
                        value = state.diagnosisHistory,
                        onValueChange = { viewModel.onDiagnosisChange(it) }
                    )
                }
            }
        }

        // Loading overlay
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = BlueMain)
            }
        }

        // Photo picker dialog
        if (showPhotoPickerDialog) {
            Dialog(onDismissRequest = { showPhotoPickerDialog = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Pilih Sumber Foto",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Camera option
                            TextButton(
                                onClick = {
                                    when (photoTypeToCapture) {
                                        PhotoType.PROFILE -> requestCameraWithPermission(PhotoType.PROFILE)
                                        PhotoType.BABY -> requestCameraWithPermission(PhotoType.BABY)
                                        else -> {}
                                    }
                                    showPhotoPickerDialog = false
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.CameraAlt,
                                        contentDescription = "Camera",
                                        tint = BlueMain
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "Kamera",
                                        color = BlueMain,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            // Gallery option
                            TextButton(
                                onClick = {
                                    when (photoTypeToCapture) {
                                        PhotoType.PROFILE -> profilePhotoLauncher.launch("image/*")
                                        PhotoType.BABY -> babyPhotoLauncher.launch("image/*")
                                        else -> {}
                                    }
                                    showPhotoPickerDialog = false
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.PhotoLibrary,
                                        contentDescription = "Gallery",
                                        tint = BlueMain
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "Galeri",
                                        color = BlueMain,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Camera capture screen
        if (showCameraForProfile) {
            CameraCaptureScreen(
                onPhotoCaptured = { uri ->
                    handleCapturedPhoto(uri, isProfile = true)
                    showCameraForProfile = false
                },
                onError = { errorMsg ->
                    scope.launch {
                        snackbarHostState.showSnackbar(errorMsg)
                    }
                },
                onClose = {
                    showCameraForProfile = false
                }
            )
        }

        // Baby photo camera capture
        if (showCameraForBaby) {
            CameraCaptureScreen(
                onPhotoCaptured = { uri ->
                    handleCapturedPhoto(uri, isProfile = false)
                    showCameraForBaby = false
                },
                onError = { errorMsg ->
                    scope.launch {
                        snackbarHostState.showSnackbar(errorMsg)
                    }
                },
                onClose = {
                    showCameraForBaby = false
                }
            )
        }
    }
}

@Composable
private fun LabeledField(label: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 16.sp, color = Color.Black)
        content()
    }
}

@Composable
private fun OutlinedInput(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    placeholder: String = ""
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = if (placeholder.isNotBlank()) {
            { Text(placeholder, color = Color.Gray) }
        } else null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFBDBDBD),
            unfocusedBorderColor = Color(0xFFD9D9D9),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
private fun GenderOption(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = BlueMain)
        )
        Spacer(Modifier.width(6.dp))
        Text(text = text, color = Color(0xFF5E5E5E), fontSize = 16.sp)
    }
}

private fun Modifier.borderStroke(width: Dp, color: Color, shape: androidx.compose.ui.graphics.Shape) =
    this.then(
        Modifier
            .clip(shape)
            .drawBehind {
                drawRoundRect(
                    color = color,
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(size.minDimension / 2, size.minDimension / 2),
                    style = Stroke(width.toPx())
                )
            }
    )

private fun Modifier.dashedBorder(width: Dp, color: Color, radius: Dp, on: Float, off: Float) =
    this.then(
        Modifier.drawBehind {
            val stroke = Stroke(
                width = width.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(on, off), 0f)
            )
            drawRoundRect(
                color = color,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(radius.toPx(), radius.toPx()),
                style = stroke
            )
        }
    )

private fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        inputStream?.copyTo(byteArrayOutputStream)
        inputStream?.close()
        byteArrayOutputStream.toByteArray()
    } catch (_: Exception) {
        null
    }
}
