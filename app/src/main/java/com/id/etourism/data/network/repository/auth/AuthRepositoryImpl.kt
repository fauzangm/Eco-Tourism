package com.id.etourism.data.network.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.id.etourism.utils.ExceptionState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.id.etourism.data.network.model.UserData
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseauth : FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthRepository {


    override fun register(result: (ExceptionState<String>) -> Unit, name: String, email: String, pw: String) {
        firebaseauth.createUserWithEmailAndPassword(email, pw)
            .addOnSuccessListener {
                val user: FirebaseUser? = firebaseauth.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                user?.updateProfile(profileUpdates)
                    ?.addOnSuccessListener {
                        result.invoke(ExceptionState.Success("User has been created succesfully"))
                    }
                    ?.addOnFailureListener {
                        result.invoke(ExceptionState.Failure("User cannot be created"))
                    }
                result.invoke(ExceptionState.Success("User has been created succesfully"))
            }
            .addOnFailureListener {
                result.invoke(ExceptionState.Failure("User cannot be created"))
            }
    }

    override fun login(result: (ExceptionState<String>) -> Unit, email: String, pw: String) {
        firebaseauth.signInWithEmailAndPassword(email, pw)
            .addOnSuccessListener {
                result.invoke(ExceptionState.Success("Data has been retrieved succesfully"))
            }
            .addOnFailureListener {
                result.invoke(ExceptionState.Failure("Data cannot be retrieved"))
            }
    }

    override fun getUserData(userId: String, result: (ExceptionState<UserData>) -> Unit) {
        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val name = document.getString("name") ?: ""
                    val email = document.getString("email") ?: ""
                    val userData = UserData(name, email)
                    result.invoke(ExceptionState.Success(userData))
                } else {
                    result.invoke(ExceptionState.Failure("User document does not exist"))
                }
            }
            .addOnFailureListener { exception ->
                result.invoke(ExceptionState.Failure(exception.message ?: "Failed to retrieve user data"))
            }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseauth.currentUser
    }
}