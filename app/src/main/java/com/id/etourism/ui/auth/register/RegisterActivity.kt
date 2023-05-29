package com.id.etourism.ui.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.id.etourism.R
import com.id.etourism.databinding.ActivityLoginBinding
import com.id.etourism.databinding.ActivityRegisterBinding
import com.id.etourism.ui.auth.login.LoginActivity
import com.id.etourism.ui.auth.login.LoginViewModel
import com.id.etourism.ui.main.MainActivity
import com.id.etourism.viewmodel.ViewModelFactory
import splitties.activities.start

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            initUi()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    private fun initUi() {
        registerViewModel = ViewModelProvider(this, ViewModelFactory(this))[RegisterViewModel::class.java]

        initAction()
    }

    private fun initAction() {
        binding.btnMasuk.setOnClickListener {
            val name = binding?.edRegisterName?.text.toString().trim()
            val email = binding?.edRegisterEmail?.text.toString().trim()
            val password = binding?.edRegisterPassword?.text.toString().trim()

            if (!isFormValid(name, email, password)) {
                Toast.makeText(this, getString(R.string.form_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerViewModel.register(name, email, password).observe(this) { result ->
                when (result) {
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> { showLoading(true) }
                    is Result.Success -> {
                        showLoading(false)
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun isFormValid(name: String, email: String, password: String): Boolean {
        val isNameValid = name.isNotEmpty()
        val isEmailValid = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotEmpty() && password.length >= 8

        return isNameValid && isEmailValid && isPasswordValid
    }
    private fun showLoading(isLoading: Boolean) { binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE }
}