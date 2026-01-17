package com.example.helldivers2companionapp.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.databinding.ActivitySquadBinding
import com.example.helldivers2companionapp.utils.SessionManager
import kotlinx.coroutines.launch

class SquadActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySquadBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySquadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        binding.rvSquad.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            // Filter: Jangan tampilkan diri sendiri di daftar rekrutmen
            val currentUserId = SessionManager(this@SquadActivity).getUserId()
            val allHelldivers = database.userDao().getAllPlayers().filter { it.id != currentUserId }

            if (allHelldivers.isEmpty()) {
                // Tampilkan teks "NO REINFORCEMENTS AVAILABLE"
            } else {
                binding.rvSquad.adapter = SquadAdapter(allHelldivers)
            }
        }
    }
}