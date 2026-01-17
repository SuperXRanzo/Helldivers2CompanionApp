package com.example.helldivers2companionapp.ui.player

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.databinding.ActivityPlayerDashboardBinding
import com.example.helldivers2companionapp.ui.login.LoginActivity
import com.example.helldivers2companionapp.utils.SessionManager
import kotlinx.coroutines.launch

class PlayerDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerDashboardBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        database = AppDatabase.getDatabase(this)

        if (!sessionManager.isLoggedIn()) {
            performLogout()
            return
        }

        setupListeners()
        loadUserStats()
    }

    private fun loadUserStats() {
        val username = sessionManager.getUsername()
        val userId = sessionManager.getUserId()

        binding.tvWelcome.text = "Welcome back, $username!"

        lifecycleScope.launch {
            try {
                val user = database.userDao().getUserById(userId)
                if (user != null) {
                    val kd = if (user.deaths > 0) user.kills.toFloat() / user.deaths.toFloat() else user.kills.toFloat()
                    binding.tvStats.text = """
                        Kills: ${user.kills}  |  Deaths: ${user.deaths}  |  K/D: ${"%.2f".format(kd)}
                        Missions Completed: ${user.missionsCompleted}
                    """.trimIndent()
                } else {
                    binding.tvStats.text = "Stats not found."
                }
            } catch (e: Exception) {
                binding.tvStats.text = "Error loading stats"
            }
        }
    }

    private fun setupListeners() {
        binding.btnStratagem.setOnClickListener {
            startActivity(Intent(this, StratagemListActivity::class.java))
        }

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.btnWarStatus.setOnClickListener {
            startActivity(Intent(this, PlayerWarStatusActivity::class.java))
        }

        // AKTIFKAN MISSION LIST
        binding.btnMission.setOnClickListener {
            startActivity(Intent(this, MissionListActivity::class.java))
        }

        // AKTIFKAN SQUAD LIST
        binding.btnSquad.setOnClickListener {
            startActivity(Intent(this, SquadActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            performLogout() // Memanggil fungsi logout di bawah
        }

        binding.btnPractice.setOnClickListener {
            startActivity(Intent(this, PracticeStratagemActivity::class.java))
        }

        binding.btnLoadout.setOnClickListener {
            startActivity(Intent(this, LoadoutActivity::class.java))
        }
    }

    private fun performLogout() {
        sessionManager.clearSession()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}