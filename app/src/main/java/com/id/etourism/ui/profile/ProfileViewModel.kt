package com.id.etourism.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id.etourism.data.network.repository.auth.AuthRepositoryImpl
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ProfileViewModel(private val authRepository: AuthRepositoryImpl): ViewModel() {
    private val _data = MutableLiveData<ExceptionState<String>>()
    val data: LiveData<ExceptionState<String>>
        get() = _data
}