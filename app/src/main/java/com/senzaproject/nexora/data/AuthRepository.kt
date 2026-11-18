package com.senzaproject.nexora.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    val isLoggedIn: Boolean get() = auth.currentUser != null
    val uid: String? get() = auth.currentUser?.uid
    val email: String get() = auth.currentUser?.email ?: ""
    val displayName: String get() = auth.currentUser?.displayName ?: ""

    suspend fun register(name: String, email: String, password: String) {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw IllegalStateException("Gagal membuat akun.")
        user.updateProfile(
            UserProfileChangeRequest.Builder().setDisplayName(name).build()
        ).await()
        val profile = UserProfile(
            uid = user.uid,
            name = name,
            email = email,
            status = "Halo, saya pakai Nexora",
            createdAt = System.currentTimeMillis()
        )
        db.collection("users").document(user.uid).set(profile).await()
    }

    suspend fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    fun logout() = auth.signOut()

    suspend fun loadProfile(): UserProfile? {
        val id = uid ?: return null
        val snap = db.collection("users").document(id).get().await()
        return snap.toObject(UserProfile::class.java)
    }

    suspend fun updateProfile(name: String, status: String) {
        val id = uid ?: throw IllegalStateException("Belum login.")
        db.collection("users").document(id)
            .update(mapOf("name" to name, "status" to status)).await()
        auth.currentUser?.updateProfile(
            UserProfileChangeRequest.Builder().setDisplayName(name).build()
        )?.await()
    }
}
