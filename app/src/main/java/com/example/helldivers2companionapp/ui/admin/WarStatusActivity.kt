package com.example.helldivers2companionapp.ui.admin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.databinding.ActivityWarStatusBinding
import kotlinx.coroutines.launch

class WarStatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWarStatusBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        // Daftar Planet
        val planets = listOf("Malevelon Creek", "Hellmire", "Veld", "Draupnir", "Crimsica")

        // Pakai Spinner Adapter Biasa
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, planets)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPlanets.adapter = adapter

        binding.btnUpdateProgress.setOnClickListener {
            val progressStr = binding.etProgress.text.toString()
            val selectedPlanet = binding.spinnerPlanets.selectedItem.toString()

            if (progressStr.isEmpty()) {
                Toast.makeText(this, "Enter progress %", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val progress = progressStr.toFloatOrNull() ?: 0f

            lifecycleScope.launch {
                // Update ke database
                database.warStatusDao().updateLiberation(selectedPlanet, progress)
                Toast.makeText(this@WarStatusActivity, "Updated $selectedPlanet to $progress%", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}