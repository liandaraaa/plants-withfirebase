package com.superprof.tumbuhan.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.superprof.tumbuhan.databinding.ActivityMainBinding
import com.superprof.tumbuhan.domain.Plant
import com.superprof.tumbuhan.presentation.PlantDetailActivity.Companion.KEY_PLANT
import com.superprof.tumbuhan.presentation.adapter.PlantAdapter
import com.superprof.tumbuhan.presentation.helper.FirebaseHelper
import com.superprof.tumbuhan.presentation.helper.FirebaseHelper.Companion.COLLECTION_PLANT

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var firebaseHelper = FirebaseHelper()

    val data = mutableListOf<Plant>()

    val plantAdapter = PlantAdapter(this, data) {
        val intent = Intent(this, PlantDetailActivity::class.java)
        intent.putExtra(KEY_PLANT, it)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseHelper.initFirebase()
        getPlants()
    }

    private fun getPlants() {
        firebaseHelper.getDatas(
            collection = COLLECTION_PLANT,
            data = Plant::class.java,
            onFailure = {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                data.clear()
                data.addAll(it)
                showPlantList()
                searchPlant()
            }
        )
    }

    private fun searchPlant() {
        binding.svPlant.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { showSearchResult(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { showSearchResult(it) }
                return false
            }

        })
    }

    private fun showSearchResult(query: String) {
        val result = data.filter { it.name.contains(query, true) }
        plantAdapter.notifyDataUpdated(result)
    }

    private fun showPlantList() {
        binding.rvPlant.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = plantAdapter
        }

    }
}