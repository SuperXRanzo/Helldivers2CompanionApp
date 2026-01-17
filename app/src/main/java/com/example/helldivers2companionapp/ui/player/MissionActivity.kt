package com.example.helldivers2companionapp.ui.player

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.databinding.ActivityMissionListBinding
import kotlinx.coroutines.launch

class MissionListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMissionListBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        binding.rvMissions.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            database.missionDao().getAllActiveMissions().collect { missions ->
                if (missions.isEmpty()) {
                    binding.rvMissions.visibility = View.GONE
                    binding.tvNoUpdate.visibility = View.VISIBLE // Otomatis terbaca dari XML
                    binding.tvNoUpdate.text = "NO MAJOR ORDERS AT THIS TIME. STAND BY."
                } else {
                    binding.rvMissions.visibility = View.VISIBLE
                    binding.tvNoUpdate.visibility = View.GONE
                    binding.rvMissions.adapter = MissionAdapter(missions)
                }
            }
        }
    }
}