package com.senzaproject.nexora.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senzaproject.nexora.data.AuthRepository
import com.senzaproject.nexora.data.UserProfile
import com.senzaproject.nexora.data.toIndonesianMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

data class AuthUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val info: String? = null
)

class AuthViewModel : ViewModel() {
    private val repo = AuthRepository()

    var state by mutableStateOf(AuthUiState())
        private set
    var profile by mutableStateOf<UserProfile?>(null)
        private set

    val isLoggedIn: Boolean get() = repo.isLoggedIn
    val displayName: String get() = repo.displayName
    val email: String get() = repo.email

    fun clearMessages() {
        state = AuthUiState()
    }

    private fun launchAuth(block: suspend () -> Unit) {
        viewModelScope.launch {
            state = AuthUiState(loading = true)
            try {
                block()
                state = state.copy(loading = false)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                state = state.copy(loading = false, error = e.toIndonesianMessage())
            }
        }
    }

    fun login(email: String, password: String, onSuccess: () -> Unit) = launchAuth {
        if (email.isBlank() || password.isBlank())
            throw IllegalArgumentException("Email dan kata sandi wajib diisi.")
        repo.login(email.trim(), password)
        onSuccess()
    }

    fun register(
        name: String, email: String, password: String, confirm: String,
        onSuccess: () -> Unit
    ) = launchAuth {
        if (name.trim().length < 2)
            throw IllegalArgumentException("Nama minimal 2 karakter.")
        if (!email.contains("@") || email.isBlank())
            throw IllegalArgumentException("Format email tidak valid.")
        if (password.length < 6)
            throw IllegalArgumentException("Kata sandi minimal 6 karakter.")
        if (password != confirm)
            throw IllegalArgumentException("Konfirmasi kata sandi tidak cocok.")
        repo.register(name.trim(), email.trim(), password)
        onSuccess()
    }

    fun resetPassword(email: String) = launchAuth {
        if (email.isBlank() || !email.contains("@"))
            throw IllegalArgumentException("Isi email dulu, lalu tekan Lupa kata sandi.")
        repo.resetPassword(email.trim())
        state = state.copy(info = "Tautan reset kata sandi dikirim ke email kamu.")
    }

    fun loadProfile() = launchAuth {
        profile = repo.loadProfile()
    }

    fun saveProfile(name: String, status: String) = launchAuth {
        if (name.trim().length < 2)
            throw IllegalArgumentException("Nama minimal 2 karakter.")
        repo.updateProfile(name.trim(), status.trim())
        profile = (profile ?: UserProfile(uid = repo.uid ?: "", email = repo.email))
            .copy(name = name.trim(), status = status.trim())
        state = state.copy(info = "Profil disimpan.")
    }

    fun logout(onDone: () -> Unit) {
        repo.logout()
        profile = null
        state = AuthUiState()
        onDone()
    }
}
