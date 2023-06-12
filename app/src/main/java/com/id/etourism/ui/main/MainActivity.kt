package com.id.etourism.ui.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.id.etourism.R
import com.id.etourism.adapter.MainAdapter
import com.id.etourism.data.network.model.Wisata
import com.id.etourism.databinding.ActivityMainBinding
import com.id.etourism.dummy.DummyData
import com.id.etourism.ui.detail.DetailActivity
import com.id.etourism.ui.profile.ProfileActivity
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var menu: Menu? = null
    private lateinit var binding : ActivityMainBinding
    private val viewmodel : MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private lateinit var wisata: ArrayList<Wisata>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.tag("siap").d(DummyData.generateDummy().toString())
        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.bg_action_bar))
        val layoutManager = LinearLayoutManager(this)
        binding.rvVillage.layoutManager = layoutManager
        wisata = ArrayList()
        adapter = MainAdapter(wisata)
        binding.rvVillage.adapter = adapter
        initUi()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.profile -> {
                val profile = Intent(this@MainActivity, ProfileActivity::class.java)
                profile.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(profile)

            }
        }
        return super.onOptionsItemSelected(item)
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
                    wisata.clear()
                    for (data in state.data){
                        wisata.add(data)
                    }
                    adapter.notifyDataSetChanged()


                    binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {

                            return false
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            searchList(newText)
                            return false
                        }

                    })
                    adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: Wisata) {
                            val extras = Bundle()
                            val intent = Intent(this@MainActivity,DetailActivity::class.java)
                            extras.putString(EXTRA_IMAGE,data.Image)
                            extras.putString(EXTRA_NAME,data.Place_Name)
                            extras.putString(EXTRA_CATEGORY,data.Category)
                            extras.putString(EXTRA_LOCATION,data.Coordinate)
                            extras.putString(EXTRA_ADDRESS,data.City)
                            extras.putString(EXTRA_RATING,data.Rating.toString())
                            extras.putString(EXTRA_DESCRIPTION,data.Description)
                                intent.putExtras(extras)
                            startActivity(intent)
                        }
                    })


                }
            }

        }
    }
    fun searchList(text: String) {
        val searchList = java.util.ArrayList<Wisata>()
        for (data in wisata) {
            if ((data.Place_Name?.lowercase())
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(data)
            }
        }
        adapter.searchDataList(searchList)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_LOCATION = "extra_location"
        const val EXTRA_ADDRESS = "extra_address"
        const val EXTRA_RATING = "extra_rating"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_IMAGE = "extra_image"
    }


}