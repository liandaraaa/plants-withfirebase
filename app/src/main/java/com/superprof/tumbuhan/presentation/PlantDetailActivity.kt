package com.superprof.tumbuhan.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.superprof.tumbuhan.R
import com.superprof.tumbuhan.databinding.ActivityMainBinding
import com.superprof.tumbuhan.databinding.ActivityPlantDetailBinding
import com.superprof.tumbuhan.domain.Plant

class PlantDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlantDetailBinding

    companion object{
        const val KEY_PLANT = "plant"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Plant>(KEY_PLANT)
        showData(data)

        binding.apply {
            supportActionBar?.apply {
                title = data?.name
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    private fun showData(data: Plant?) {
        data?.let {
            Glide.with(this).load(data.imageUrl).into(binding.ivPlant)
            binding.tvDescription.text = data.description
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}