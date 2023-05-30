package com.id.etourism.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.id.etourism.R
import com.id.etourism.databinding.ActivityProfileBinding
import com.id.etourism.utils.ExceptionState
import com.id.etourism.viewmodel.ViewModelFactory
import timber.log.Timber

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profile Story"

        profileViewModel = ViewModelProvider(this, ViewModelFactory(this))[ProfileViewModel::class.java]

        profileViewModel.data.observe(this) { state ->
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
}