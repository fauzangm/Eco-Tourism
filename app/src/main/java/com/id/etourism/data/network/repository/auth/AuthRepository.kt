package com.id.etourism.data.network.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.id.etourism.data.network.model.UserData
import com.id.etourism.data.network.model.Wisata
import com.id.etourism.utils.ExceptionState

interface AuthRepository {
    fun register(result: (ExceptionState<String>) -> Unit, name: String, email: String, pw: String)

    fun login(result: (ExceptionState<String>) -> Unit, email: String, pw: String)

    fun getUserData(userId: String, result: (ExceptionState<UserData>) -> Unit)

    fun getCurrentUser(): FirebaseUser?
}