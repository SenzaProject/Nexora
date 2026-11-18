package com.senzaproject.nexora.data

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestoreException

fun Throwable.toIndonesianMessage(): String = when (this) {
    is FirebaseAuthWeakPasswordException -> "Kata sandi terlalu lemah (minimal 6 karakter)."
    is FirebaseAuthInvalidCredentialsException -> "Email atau kata sandi salah."
    is FirebaseAuthInvalidUserException -> "Akun tidak ditemukan."
    is FirebaseAuthUserCollisionException -> "Email sudah terdaftar."
    is FirebaseNetworkException -> "Tidak ada koneksi internet."
    is FirebaseTooManyRequestsException -> "Terlalu banyak percobaan. Coba lagi nanti."
    is FirebaseFirestoreException ->
        if (code == FirebaseFirestoreException.Code.PERMISSION_DENIED)
            "Akses ditolak. Periksa aturan (rules) Firestore."
        else "Kesalahan database: ${code.name}"
    else -> message ?: "Terjadi kesalahan."
}
