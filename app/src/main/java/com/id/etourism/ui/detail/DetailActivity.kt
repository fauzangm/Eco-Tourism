package com.id.etourism.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.id.etourism.R
import com.id.etourism.ui.main.MainActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val data = intent.getStringArrayListExtra(MainActivity.EXTRA_ID)
    }

}