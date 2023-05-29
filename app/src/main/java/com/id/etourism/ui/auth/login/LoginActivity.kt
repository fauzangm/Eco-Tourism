package com.id.etourism.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.id.etourism.R
import com.id.etourism.databinding.ActivityLoginBinding
import com.id.etourism.ui.main.MainActivity
import com.id.etourism.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            initUi()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initUi() {
        loginViewModel = ViewModelProvider(this, ViewModelFactory(this))[LoginViewModel::class.java]

        initAction()
    }

    private fun initAction() {
        binding.btnMasuk.setOnClickListener {
            val email = binding?.etUsername?.text.toString().trim()
            val password = binding?.etPassword?.text.toString().trim()

            if (!isFormValid(email, password)) {
                Toast.makeText(this, getString(R.string.form_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginViewModel.login(email, password).observe(this) { result ->
                when (result) {
                    is Result.Loading -> { showLoading(true) }
                    is Result.Success -> {
                        showLoading(false)
                        Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                        start<MainActivity>()
                        finish()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun isFormValid(email: String, password: String): Boolean {
        val isEmailValid = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotEmpty() && password.length >= 8

        return isEmailValid && isPasswordValid
    }

    private fun showLoading(isLoading: Boolean) { binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE }

}