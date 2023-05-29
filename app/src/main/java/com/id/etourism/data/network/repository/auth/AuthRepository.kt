package com.id.etourism.data.network.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.id.etourism.data.network.RemoteDataSource

class AuthRepository(private val remoteDataSource: RemoteDataSource, private val userPreference: UserPreference) {
    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.login(email, password)
            userPreference.saveToken(response.loginResult?.token.toString())
            userPreference.saveLogin(true)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun isLogin() = userPreference.isLogin().asLiveData()

    suspend fun logout() {
        userPreference.run {
            saveLogin(false)
            deleteToken()
        }
    }
}