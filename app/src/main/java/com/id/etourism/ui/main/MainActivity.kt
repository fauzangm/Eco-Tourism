package com.id.etourism.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.id.etourism.adapter.MainAdapter
import com.id.etourism.data.network.model.Wisata
import com.id.etourism.databinding.ActivityMainBinding
import com.id.etourism.ui.detail.DetailActivity
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewmodel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()

    }

    private fun initUi() {
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
                    val adapter = MainAdapter(state.data)
                    val layoutManager = LinearLayoutManager(this)
                    binding.rvVillage.layoutManager = layoutManager
                    binding.rvVillage.adapter = adapter
                    adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: Wisata) {
                            val intent = Intent(this@MainActivity,DetailActivity::class.java)
                            intent.putExtra(EXTRA_ID,data.wisata)
                            startActivity(intent)
                        }
                    })


                }
            }

        }
    }
    companion object {
        const val EXTRA_ID = "extra_id"
    }

}