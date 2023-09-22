package com.example.cinemate.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class UserRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
)  {

    suspend fun getFirebaseUserUid(): String = firebaseAuth.currentUser?.uid.orEmpty()

}