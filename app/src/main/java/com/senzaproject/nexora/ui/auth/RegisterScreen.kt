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
fun RegisterScreen(vm: AuthViewModel, onBack: () -> Unit, onSuccess: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    val state = vm.state

    AuthScaffold(title = "Buat akun Nexora", subtitle = "Gratis dan cepat") {
        NexoraField(name, { name = it }, "Nama")
        Spacer(Modifier.height(12.dp))
        NexoraField(email, { email = it }, "Email", keyboardType = KeyboardType.Email)
        Spacer(Modifier.height(12.dp))
        NexoraField(password, { password = it }, "Kata sandi", isPassword = true)
        Spacer(Modifier.height(12.dp))
        NexoraField(confirm, { confirm = it }, "Ulangi kata sandi", isPassword = true)
        MessageText(state)
        Spacer(Modifier.height(16.dp))
        NexoraButton("Daftar", state.loading) {
            vm.register(name, email, password, confirm, onSuccess)
        }
        TextButton(onClick = { vm.clearMessages(); onBack() }) {
            Text("Sudah punya akun? Masuk")
        }
    }
}
