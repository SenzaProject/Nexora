package com.senzaproject.nexora.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.senzaproject.nexora.ui.auth.AuthViewModel
import com.senzaproject.nexora.ui.auth.LoginScreen
import com.senzaproject.nexora.ui.auth.RegisterScreen
import com.senzaproject.nexora.ui.profile.ProfileScreen
import kotlinx.coroutines.delay

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val PROFILE = "profile"
}

@Composable
fun NexoraApp(vm: AuthViewModel = viewModel()) {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1800)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        val nav = rememberNavController()
        NavHost(
            navController = nav,
            startDestination = if (vm.isLoggedIn) Routes.HOME else Routes.LOGIN
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    vm = vm,
                    onRegister = { nav.navigate(Routes.REGISTER) },
                    onSuccess = {
                        nav.navigate(Routes.HOME) { popUpTo(0) { inclusive = true } }
                    }
                )
            }
            composable(Routes.REGISTER) {
                RegisterScreen(
                    vm = vm,
                    onBack = { nav.popBackStack() },
                    onSuccess = {
                        nav.navigate(Routes.HOME) { popUpTo(0) { inclusive = true } }
                    }
                )
            }
            composable(Routes.HOME) {
                HomeScreen(
                    name = vm.displayName,
                    onProfile = { nav.navigate(Routes.PROFILE) }
                )
            }
            composable(Routes.PROFILE) {
                ProfileScreen(
                    vm = vm,
                    onBack = { nav.popBackStack() },
                    onLoggedOut = {
                        nav.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } }
                    }
                )
            }
        }
    }
}
