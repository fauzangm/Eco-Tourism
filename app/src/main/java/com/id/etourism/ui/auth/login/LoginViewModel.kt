package com.id.etourism.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.id.etourism.data.network.repository.auth.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun login(email: String, password: String): LiveData<Result<LoginResponse>> {
        return authRepository.login(email, password)
    }
}