package com.senzaproject.nexora.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.senzaproject.nexora.R
import com.senzaproject.nexora.ui.theme.NexoraBlue
import com.senzaproject.nexora.ui.theme.NexoraBlueDark
import com.senzaproject.nexora.ui.theme.NexoraWhite

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(NexoraBlue, NexoraBlueDark))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo_nexora),
            contentDescription = "Logo Nexora",
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(32.dp))
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Nexora",
            color = NexoraWhite,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "by SenzaProject",
            color = NexoraWhite.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
    }
}
