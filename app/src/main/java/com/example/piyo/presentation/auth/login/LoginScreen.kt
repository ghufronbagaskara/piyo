package com.example.piyo.presentation.auth.login

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
import com.example.piyo.domain.usecase.child.CheckUserHasChildUseCase
import com.example.piyo.presentation.navigation.InfoAnakRoute
import com.example.piyo.presentation.navigation.MainRoute
import com.example.piyo.presentation.navigation.RegisterRoute
import com.example.piyo.ui.theme.BlueMain
import com.example.piyo.ui.theme.YellowMain
import com.example.piyo.util.FirebaseUtils
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    navController: NavController,
    checkUserHasChildUseCase: CheckUserHasChildUseCase = koinInject()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rememberMe by rememberSaveable { mutableStateOf(false) }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val isValid = email.isNotBlank() && password.isNotBlank()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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

            Spacer(Modifier.height(36.dp))
            Text(
                text = "Masuk",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )

            Spacer(Modifier.height(20.dp))
            Text(text = "Email", fontSize = 16.sp, color = Color.Black)
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
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

            Spacer(Modifier.height(16.dp))
            Text(text = "Kata Sandi", fontSize = 16.sp, color = Color.Black)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            painter = painterResource(if (showPassword) R.drawable.ic_showpw else R.drawable.ic_hidepw),
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

            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    colors = CheckboxDefaults.colors(checkedColor = BlueMain)
                )
                Text(text = "Ingat saya", color = Color(0xFF5E5E5E), fontSize = 16.sp)
            }

            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        try {
                            val user = FirebaseUtils.loginUser(email, password)
                            if (user != null) {
                                // Check if user already has child data
                                val hasChild = checkUserHasChildUseCase(user.uid)
                                if (hasChild) {
                                    // User has child, go to main screen
                                    navController.navigate(MainRoute) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                } else {
                                    // User doesn't have child, go to info anak
                                    navController.navigate(InfoAnakRoute) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            } else {
                                snackbarHostState.showSnackbar("Login gagal. Periksa kembali akun Anda.")
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
                    containerColor = BlueMain,
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
                    Text("Masuk", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }


                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    TextButton(onClick = { /* TODO: Implementasi navigasi lupa password */ }) {
                                        Text(
                                            text = "Lupa password?",
                                            color = BlueMain,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Belum punya akun? ", color = Color.Black, fontSize = 16.sp)
                TextButton(onClick = { navController.navigate(RegisterRoute) }) {
                    Text(text = "Daftar", color = YellowMain, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
