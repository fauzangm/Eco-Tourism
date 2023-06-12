package com.id.etourism.ui.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.id.etourism.R
import com.id.etourism.databinding.ActivityLoginBinding
import com.id.etourism.databinding.ActivityRegisterBinding
import com.id.etourism.ui.auth.login.LoginActivity
import com.id.etourism.ui.auth.login.LoginViewModel
import com.id.etourism.ui.main.MainActivity
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start
import timber.log.Timber

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private val registerViewModel : RegisterViewModel by viewModels()
    private var selectedGender: String = ""

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
        supportActionBar?.hide()
        setupGenderSpinner()
        initAction()
        initObserve()
    }

    private fun initObserve() {
        registerViewModel.data.observe(this) { state ->
            when (state) {
                is ExceptionState.Loading -> {
                    Timber.tag("loading").e("loading...")
                }

                is ExceptionState.Failure -> {
                    Timber.tag("gagal").e(state.error)
                }

                is ExceptionState.Success -> {
                    Timber.tag("success").e("${state.data}")
                }
            }
        }
    }

    private fun initAction() {
        binding.btnMasuk.setOnClickListener {
            val name = binding?.etNama?.text.toString().trim()
            val email = binding?.etUsername?.text.toString().trim()
            val password = binding?.etPassword?.text.toString().trim()
            val alamat = binding?.etAlamat?.text.toString().trim()
            val kontak = binding?.etNoTelp?.text.toString().trim()

            if (!isFormValid(name, email, password)) {
                Toast.makeText(this, getString(R.string.form_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerViewModel.register(name, email, password)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.imgBackRegist.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupGenderSpinner() {
        val genderSpinnerAdapter = ArrayAdapter.createFromResource(
            this, R.array.list_gender,
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinGender.adapter = genderSpinnerAdapter
//        dataRegistrasiCache.dataRegistrasi?.wajibPajak?.gender?.let { binding.spinGender.setSelection(it) }
        binding.spinGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedGender = parent?.getItemAtPosition(position).toString()
                Log.e("gender",selectedGender)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    private fun isFormValid(name: String, email: String, password: String): Boolean {
        val isNameValid = name.isNotEmpty()
        val isEmailValid = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotEmpty() && password.length >= 8

        return isNameValid && isEmailValid && isPasswordValid
    }
}