package com.example.piyo.presentation.infoanak

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piyo.R
import com.example.piyo.presentation.navigation.MainRoute
import com.example.piyo.ui.theme.BlueMain
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.imePadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoAnakScreen(navController: NavController) {
    var name by rememberSaveable { mutableStateOf("") }
    var birth by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf<String?>(null) }
    var weight by rememberSaveable { mutableStateOf("") }
    var diagnosis by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Informasi Anak", fontWeight = FontWeight.Bold, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                }
            )
        },
        containerColor = Color.White,
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
                    .imePadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = { navController.navigate(MainRoute) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BlueMain)
                ) {
                    Text("Lanjut", color = Color.White, fontWeight = FontWeight.SemiBold)
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
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_child_placeholder),
                                contentDescription = "Foto Si Kecil",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .borderStroke(2.dp, BlueMain, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit", tint = BlueMain)
                    }
                }
            }

            item {
                LabeledField(label = "Nama Lengkap") {
                    OutlinedInput(value = name, onValueChange = { name = it })
                }
            }

            item {
                LabeledField(label = "Tanggal Lahir") {
                    OutlinedInput(
                        value = birth,
                        onValueChange = { birth = it },
                        keyboardType = KeyboardType.Number
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
                            selected = gender == "P",
                            onClick = { gender = "P" }
                        )
                        GenderOption(
                            text = "Laki-Laki",
                            selected = gender == "L",
                            onClick = { gender = "L" }
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
                Text(text = "Foto Si Kecil", fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .dashedBorder(2.dp, BlueMain, 32.dp, on = 14f, off = 10f)
                        .clip(RoundedCornerShape(32.dp))
                        .clickable { /* TODO: pick image */ },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = BlueMain)
                        Spacer(Modifier.width(6.dp))
                        Text(text = "+ Unggah Foto", color = BlueMain, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            item {
                LabeledField(label = "Berat Badan Saat Lahir (Kg)") {
                    OutlinedInput(
                        value = weight,
                        onValueChange = { weight = it },
                        keyboardType = KeyboardType.Decimal
                    )
                }
            }

            item {
                LabeledField(label = "Riwayat Diagnosis") {
                    OutlinedInput(
                        value = diagnosis,
                        onValueChange = { diagnosis = it }
                    )
                }
            }
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
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
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
