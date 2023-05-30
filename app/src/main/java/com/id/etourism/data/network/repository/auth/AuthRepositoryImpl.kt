package com.id.etourism.data.network.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.id.etourism.utils.ExceptionState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class AuthRepositoryImpl(
    private val firebaseauth : FirebaseAuth
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
}