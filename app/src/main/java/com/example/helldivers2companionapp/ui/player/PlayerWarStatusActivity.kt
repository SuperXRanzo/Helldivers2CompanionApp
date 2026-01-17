package com.example.helldivers2companionapp.ui.player

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.databinding.ActivityPlayerWarStatusBinding
import kotlinx.coroutines.launch

class PlayerWarStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerWarStatusBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerWarStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        binding.rvWarStatus.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            database.warStatusDao().getAllPlanets().collect { planets ->
                if (planets.isEmpty()) {
                    binding.tvNoUpdate.visibility = View.VISIBLE
                    binding.rvWarStatus.visibility = View.GONE
                } else {
                    binding.tvNoUpdate.visibility = View.GONE
                    binding.rvWarStatus.visibility = View.VISIBLE
                    // Set adapter war status di sini
                }
            }
        }
    }
}