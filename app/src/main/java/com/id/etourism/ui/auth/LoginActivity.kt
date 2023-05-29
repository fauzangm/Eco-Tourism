package com.id.etourism.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.id.etourism.R
import com.id.etourism.databinding.ActivityLoginBinding
import com.id.etourism.databinding.ActivityMainBinding
import com.id.etourism.ui.MainActivity
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var firebaseauth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            firebaseauth =  FirebaseAuth.getInstance()
            initUi()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initUi() {
        binding.btnMasuk.setOnClickListener {
            //val email = binding.tiUsername.text.toString()
            //val password = binding.tiPassword.text.toString()
            loginUser("tesuser@gmail.com","12341234")

        }

    }
    private fun loginUser(email: String, pw: String) {
        firebaseauth.signInWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.e("Succes Login", "succes")
                    start<MainActivity>()
                    finish()

                } else {
                    Log.e("Error Login", "error")
                }
            }
    }


}