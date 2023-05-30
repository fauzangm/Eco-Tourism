package com.id.etourism.data.network.repository.auth

import com.id.etourism.data.network.model.Wisata
import com.id.etourism.utils.ExceptionState

interface AuthRepository {
    fun register(result: (ExceptionState<String>) -> Unit, name: String, email: String, pw: String)

    fun login(result: (ExceptionState<String>) -> Unit, email: String, pw: String)
}