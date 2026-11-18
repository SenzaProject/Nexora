package com.senzaproject.nexora.ui.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(vm: AuthViewModel, onRegister: () -> Unit, onSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state = vm.state

    AuthScaffold(title = "Masuk ke Nexora", subtitle = "Lanjutkan percakapanmu") {
        NexoraField(email, { email = it }, "Email", keyboardType = KeyboardType.Email)
        Spacer(Modifier.height(12.dp))
        NexoraField(password, { password = it }, "Kata sandi", isPassword = true)
        MessageText(state)
        Spacer(Modifier.height(16.dp))
        NexoraButton("Masuk", state.loading) { vm.login(email, password, onSuccess) }
        TextButton(onClick = { vm.resetPassword(email) }) { Text("Lupa kata sandi?") }
        TextButton(onClick = { vm.clearMessages(); onRegister() }) {
            Text("Belum punya akun? Daftar")
        }
    }
}
