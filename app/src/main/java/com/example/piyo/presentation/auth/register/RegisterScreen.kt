package com.example.piyo.presentation.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piyo.R
import com.example.piyo.presentation.navigation.InfoAnakRoute
import com.example.piyo.presentation.navigation.LoginRoute
import com.example.piyo.ui.theme.BlueMain
import com.example.piyo.ui.theme.YellowMain
import com.example.piyo.util.FirebaseUtils
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {
    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }
    var showPw by rememberSaveable { mutableStateOf(false) }
    var showPw2 by rememberSaveable { mutableStateOf(false) }
    var agree by rememberSaveable { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    val allFilled = fullName.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && password.isNotBlank() && confirm.isNotBlank()
    val isValid = allFilled && password == confirm && agree

    Scaffold(
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_piyo_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(width = 76.dp, height = 67.dp)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.splash_text),
                        contentDescription = "Piyo",
                        modifier = Modifier.size(height = 56.dp, width = 85.dp)
                    )
                    Text(
                        text = "Bersama Tumbuh, Berkembang Lebih Baik",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            LabeledTextField(
                label = "Nama Lengkap",
                value = fullName,
                onValueChange = { fullName = it }
            )

            Spacer(Modifier.height(16.dp))
            LabeledTextField(
                label = "Email",
                value = email,
                onValueChange = { email = it }
            )

            Spacer(Modifier.height(16.dp))
            LabeledTextField(
                label = "Nomor Handphone",
                value = phone,
                onValueChange = { phone = it },
                keyboardType = KeyboardType.Phone
            )

            Spacer(Modifier.height(16.dp))
            LabeledPasswordField(
                label = "Kata Sandi",
                value = password,
                onValueChange = { password = it },
                visible = showPw,
                onToggleVisible = { showPw = !showPw }
            )

            Spacer(Modifier.height(16.dp))
            LabeledPasswordField(
                label = "Konfirmasi Kata Sandi",
                value = confirm,
                onValueChange = { confirm = it },
                visible = showPw2,
                onToggleVisible = { showPw2 = !showPw2 }
            )

            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.Top) {
                Checkbox(
                    checked = agree,
                    onCheckedChange = { agree = it },
                    colors = CheckboxDefaults.colors(checkedColor = BlueMain)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Dengan memilih \"Saya setuju\",\nAnda menyetujui Kebijakan Privasi Piyo",
                    color = Color(0xFF5E5E5E),
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        try {
                            val user = FirebaseUtils.registerUser(email, password)
                            if (user != null) {
                                // After registration, always go to InfoAnakRoute
                                navController.navigate(InfoAnakRoute) {
                                    popUpTo(0) { inclusive = true }
                                }
                            } else {
                                snackbarHostState.showSnackbar("Registrasi gagal. Coba lagi.")
                            }
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar("Error: ${e.message}")
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowMain,
                    disabledContainerColor = Color(0xFFBDBDBD)
                ),
                enabled = isValid && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text("Daftar", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }


            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Sudah punya akun? ", color = Color.Black, fontSize = 16.sp)
                TextButton(onClick = { navController.navigate(LoginRoute) }) {
                    Text(text = "Masuk", color = YellowMain, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Text(text = label, fontSize = 16.sp, color = Color.Black)
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
private fun LabeledPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    visible: Boolean,
    onToggleVisible: () -> Unit
) {
    Text(text = label, fontSize = 16.sp, color = Color.Black)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = onToggleVisible) {
                Icon(
                    painter = painterResource(if (visible) R.drawable.ic_showpw else R.drawable.ic_hidepw),
                    contentDescription = "Toggle password",
                    tint = Color(0xFF8E8E8E)
                )
            }
        },
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
