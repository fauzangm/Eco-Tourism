package com.id.etourism.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id.etourism.data.network.repository.auth.AuthRepository
import com.id.etourism.data.network.repository.auth.AuthRepositoryImpl
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val authRepository: AuthRepository
    ) : ViewModel() {
    private val _data = MutableLiveData<ExceptionState<String>>()
    val data: LiveData<ExceptionState<String>>
        get() = _data

    fun login(email: String, password: String) {
        _data.value = ExceptionState.Loading
        authRepository.login({_data.value=it}, email, password)
    }
}