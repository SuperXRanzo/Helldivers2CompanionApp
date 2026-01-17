package com.example.helldivers2companionapp.ui.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.data.model.Mission
import com.example.helldivers2companionapp.databinding.ActivityManageMissionsBinding
import kotlinx.coroutines.launch

class ManageMissionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageMissionsBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageMissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        binding.btnSaveMission.setOnClickListener {
            val mission = Mission(
                planetName = binding.etPlanetName.text.toString(),
                difficulty = binding.etDifficulty.text.toString().toIntOrNull() ?: 1,
                type = binding.etType.text.toString(),
                reward = binding.etReward.text.toString().toIntOrNull() ?: 0,
                description = "Tactical objective updated."
            )

            lifecycleScope.launch {
                database.missionDao().insertMission(mission)
                Toast.makeText(this@ManageMissionsActivity, "Mission Deployed!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}