package com.example.sistemaventilacion.data.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class ResetPasswordRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
) {
    suspend fun sendPassword(email: String){
        auth.sendPasswordResetEmail(email).await()
    }
}