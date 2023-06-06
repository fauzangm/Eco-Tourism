package com.id.etourism.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.id.etourism.R
import com.id.etourism.databinding.ActivityProfileBinding
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profile"

        viewModel.data.observe(this) { state ->
            when (state) {
                is ExceptionState.Success -> {
                    binding.tvName.text = state.data.name
                    binding.tvEmail.text = state.data.email
                }

                is ExceptionState.Failure -> {
                    // Handle the failure state
                    Toast.makeText(this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
                }

                is ExceptionState.Loading -> {
                    // Show loading progress, such as displaying a progress bar
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }


        // Call the function to retrieve data from Firestore
        viewModel.retrieveUserData()
    }
}
