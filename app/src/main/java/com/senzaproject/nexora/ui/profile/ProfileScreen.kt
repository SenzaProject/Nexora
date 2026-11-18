package com.senzaproject.nexora.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.senzaproject.nexora.ui.auth.AuthViewModel
import com.senzaproject.nexora.ui.auth.MessageText
import com.senzaproject.nexora.ui.auth.NexoraButton
import com.senzaproject.nexora.ui.auth.NexoraField
import com.senzaproject.nexora.ui.theme.NexoraRed
import com.senzaproject.nexora.ui.theme.NexoraWhite

@Composable
fun ProfileScreen(vm: AuthViewModel, onBack: () -> Unit, onLoggedOut: () -> Unit) {
    LaunchedEffect(Unit) { vm.loadProfile() }

    val profile = vm.profile
    var name by remember(profile) { mutableStateOf(profile?.name ?: vm.displayName) }
    var status by remember(profile) { mutableStateOf(profile?.status ?: "") }
    val state = vm.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextButton(onClick = { vm.clearMessages(); onBack() }, modifier = Modifier.align(Alignment.Start)) {
            Text("< Kembali")
        }
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.trim().firstOrNull()?.uppercase() ?: "N",
                color = NexoraWhite,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(vm.email, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
        Spacer(Modifier.height(24.dp))
        NexoraField(name, { name = it }, "Nama")
        Spacer(Modifier.height(12.dp))
        NexoraField(status, { status = it }, "Status")
        MessageText(state)
        Spacer(Modifier.height(16.dp))
        NexoraButton("Simpan", state.loading) { vm.saveProfile(name, status) }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = { vm.logout(onLoggedOut) },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Keluar", color = NexoraRed, fontWeight = FontWeight.SemiBold)
        }
    }
}
