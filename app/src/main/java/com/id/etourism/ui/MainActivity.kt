package com.id.etourism.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.id.etourism.R
import com.id.etourism.databinding.ActivityMainBinding
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var firebaseauth : FirebaseAuth
    private lateinit var binding : ActivityMainBinding
    private val viewmodel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            firebaseauth =  FirebaseAuth.getInstance()
            initUi()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initUi() {
        loginUser("tesuser@gmail.com","12341234")
        viewmodel.getWisata()
        viewmodel.data.observe(this){ state ->
            when(state){
                is ExceptionState.Loading -> {
                    Timber.tag("loading").e("loading...")
                }
                is ExceptionState.Failure -> {
                    Timber.tag("gagal").e(state.error)
                }
                is ExceptionState.Success -> {
                    Timber.tag("succes").e("${state.data}")
                }
            }

        }
    }

    private fun loginUser(email: String, pw: String) {

        firebaseauth.signInWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.e("Succes Login", "succes")

                } else {
                    Log.e("Error Login", "error")
                }
            }
    }

}