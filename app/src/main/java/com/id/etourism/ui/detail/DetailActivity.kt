package com.id.etourism.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.id.etourism.R
import com.id.etourism.databinding.ActivityDetailBinding
import com.id.etourism.databinding.ActivityMainBinding
import com.id.etourism.ui.main.MainActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        if (extras != null) {
            show(
                extras.getString(MainActivity.EXTRA_NAME,""),
                extras.getString(MainActivity.EXTRA_CATEGORY,""),
                extras.getString(MainActivity.EXTRA_ADDRESS,""),
                extras.getString(MainActivity.EXTRA_LOCATION,""),
                extras.getString(MainActivity.EXTRA_DESCRIPTION,""),
                extras.getString(MainActivity.EXTRA_RATING,""),


                )
        }
    }
    fun show(name:String,category: String,address:String,location:String, description:String,rating:String ){
        binding.tvVillage.text = name
        binding.tvCategory.text = category
        binding.tvAddress.text = address
        binding.tvLocation.text = location
        binding.tvDescription.text = description
        binding.tvRating.text = rating
    }

}