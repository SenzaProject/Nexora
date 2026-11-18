package com.senzaproject.nexora.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.senzaproject.nexora.R
import com.senzaproject.nexora.ui.theme.NexoraBlue
import com.senzaproject.nexora.ui.theme.NexoraBlueDark
import com.senzaproject.nexora.ui.theme.NexoraRed
import com.senzaproject.nexora.ui.theme.NexoraWhite

@Composable
fun AuthScaffold(
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(NexoraBlue, NexoraBlueDark)))
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo_nexora),
            contentDescription = "Logo Nexora",
            modifier = Modifier.size(84.dp).clip(RoundedCornerShape(20.dp))
        )
        Spacer(Modifier.height(14.dp))
        Text(title, color = NexoraWhite, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = NexoraWhite.copy(alpha = 0.75f), fontSize = 14.sp)
        Spacer(Modifier.height(20.dp))
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content
            )
        }
    }
}

@Composable
fun NexoraField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var visible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        visualTransformation =
            if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else keyboardType
        ),
        trailingIcon = {
            if (isPassword) {
                TextButton(onClick = { visible = !visible }) {
                    Text(if (visible) "Sembunyi" else "Lihat")
                }
            }
        }
    )
}

@Composable
fun NexoraButton(text: String, loading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = !loading,
        modifier = Modifier.fillMaxWidth().height(50.dp)
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp,
                color = NexoraWhite
            )
        } else {
            Text(text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun MessageText(state: AuthUiState) {
    state.error?.let {
        Spacer(Modifier.height(10.dp))
        Text(it, color = NexoraRed, fontSize = 13.sp)
    }
    state.info?.let {
        Spacer(Modifier.height(10.dp))
        Text(it, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
    }
}
