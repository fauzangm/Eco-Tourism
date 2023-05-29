package com.id.etourism.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.id.etourism.data.network.repository.auth.AuthRepository

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> {
        return authRepository.register(name, email, password)
    }
}