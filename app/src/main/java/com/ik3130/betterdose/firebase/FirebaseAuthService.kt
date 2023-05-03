package com.ik3130.betterdose.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthService {
    fun login(
        email: String,
        password: String,
        firebaseAuth: FirebaseAuth,
        onSuccess: () -> Unit,
        onFailure: (java.lang.Exception?) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    Log.i("LOGIN ERR", it.exception?.message.toString())
                    onFailure(it.exception)
                }
            }
    }

    fun logout(firebaseAuth: FirebaseAuth) {
        firebaseAuth.signOut()
    }

    fun register(
        email: String,
        password: String,
        firebaseAuth: FirebaseAuth,
        onSuccess: () -> Unit,
        onFailure: (java.lang.Exception?) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess()
            } else {
                Log.i("REGISTER ERR", it.exception?.message.toString())
                onFailure(it.exception)
            }
        }
    }
}