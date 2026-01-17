package com.example.helldivers2companionapp.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.databinding.ActivityAnalyticsBinding
import kotlinx.coroutines.launch

class AnalyticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnalyticsBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.Companion.getDatabase(this)

        lifecycleScope.launch {
            // 1. Ambil semua user (pastikan di UserDao sudah ada getAllPlayers)
            val allPlayers = database.userDao().getAllPlayers()

            // 2. Hitung Manual Statistiknya
            var totalKills = 0
            var totalDeaths = 0
            var totalMissions = 0

            for (player in allPlayers) {
                totalKills += player.kills
                totalDeaths += player.deaths
                totalMissions += player.missionsCompleted
            }

            // 3. Update UI
            binding.tvTotalHelldivers.text = "${allPlayers.size}"
            binding.tvTotalKills.text = "$totalKills"
            binding.tvTotalDeaths.text = "$totalDeaths"

            // Hitung Success Rate (Misi vs Kematian sebagai contoh kasar)
            val rate =
                if (totalDeaths > 0) (totalMissions.toFloat() * 100 / (totalMissions + totalDeaths)) else 100f
            binding.tvSuccessRate.text = "${"%.1f".format(rate)}%"
        }
    }
}