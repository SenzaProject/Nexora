package com.senzaproject.nexora.data

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val status: String = "",
    val createdAt: Long = 0L
)
