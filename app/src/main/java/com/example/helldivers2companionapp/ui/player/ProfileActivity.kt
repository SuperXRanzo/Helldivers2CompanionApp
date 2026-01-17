package com.example.helldivers2companionapp.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.databinding.ActivityProfileBinding
import com.example.helldivers2companionapp.utils.SessionManager
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        database = AppDatabase.getDatabase(this)

        loadProfileData()

        binding.btnLogout.setOnClickListener {
            sessionManager.clearSession()
            // Kembali ke Login...
            finish()
        }
    }

    private fun loadProfileData() {
        val userId = sessionManager.getUserId()
        val username = sessionManager.getUsername()

        binding.tvUsername.text = username

        lifecycleScope.launch {
            val user = database.userDao().getUserById(userId)
            user?.let {
                binding.tvKills.text = it.kills.toString()
                binding.tvDeaths.text = it.deaths.toString()
                binding.tvMissions.text = it.missionsCompleted.toString()

                val kd = if (it.deaths > 0) it.kills.toFloat() / it.deaths.toFloat() else it.kills.toFloat()
                binding.tvKDRatio.text = "%.2f".format(kd)
            }
        }
    }
}