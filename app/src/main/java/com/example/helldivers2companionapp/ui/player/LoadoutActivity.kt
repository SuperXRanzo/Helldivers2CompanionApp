package com.example.helldivers2companionapp.ui.player

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.data.model.Loadout
import com.example.helldivers2companionapp.databinding.ActivityLoadoutBinding
import com.example.helldivers2companionapp.utils.SessionManager
import kotlinx.coroutines.launch

class LoadoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadoutBinding
    private lateinit var database: AppDatabase
    private lateinit var sessionManager: SessionManager

    // 1. Data Hardcoded untuk Senjata (10-15 Item)
    private val primaryWeapons = listOf(
        "AR-23 Liberator",
        "R-63 Diligence",
        "SMG-37 Defender",
        "SG-225 Breaker",
        "PLAS-1 Scorcher",
        "JAR-5 Dominator",
        "LAS-5 Scythe",
        "AR-23P Liberator Pen",
        "R-63CS Diligence CS",
        "SG-8S Slugger"
    )
    private val secondaryWeapons = listOf(
        "P-2 Peacemaker",
        "P-19 Redeemer",
        "P-4 Senator",
        "LAS-7 Dagger",
        "GP-31 Grenade Pistol"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        sessionManager = SessionManager(this)

        setupSpinners()
        setupListeners()
        loadSavedLoadouts()
    }

    private fun setupSpinners() {
        // Setup Senjata (Static)
        val primaryAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, primaryWeapons)
        binding.spPrimary.adapter = primaryAdapter

        val secondaryAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, secondaryWeapons)
        binding.spSecondary.adapter = secondaryAdapter

        // Setup Stratagem (Dynamic dari DB Admin)
        lifecycleScope.launch {
            // Ambil semua stratagem yang ada di DB
            val stratagems = database.stratagemDao()
                .getAllStratagemsList() // Pastikan fungsi ini return List<Stratagem>

            val stratagemNames = if (stratagems.isNotEmpty()) {
                stratagems.map { it.name }
            } else {
                listOf("No Stratagems Available")
            }

            val stratAdapter = ArrayAdapter(
                this@LoadoutActivity,
                android.R.layout.simple_spinner_dropdown_item,
                stratagemNames
            )

            // Pasang ke 4 Spinner sekaligus
            binding.spStrat1.adapter = stratAdapter
            binding.spStrat2.adapter = stratAdapter
            binding.spStrat3.adapter = stratAdapter
            binding.spStrat4.adapter = stratAdapter
        }
    }

    private fun setupListeners() {
        binding.btnSaveLoadout.setOnClickListener {
            val name = binding.etLoadoutName.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, "Please name your loadout!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ambil value dari Spinner
            val pWeapon = binding.spPrimary.selectedItem.toString()
            val sWeapon = binding.spSecondary.selectedItem.toString()
            val st1 = binding.spStrat1.selectedItem.toString()
            val st2 = binding.spStrat2.selectedItem.toString()
            val st3 = binding.spStrat3.selectedItem.toString()
            val st4 = binding.spStrat4.selectedItem.toString()

            val userId = sessionManager.getUserId()

            // Simpan ke DB
            lifecycleScope.launch {
                val newLoadout = Loadout(
                    userId = userId,
                    name = name,
                    primaryWeapon = pWeapon,
                    secondaryWeapon = sWeapon,
                    stratagem1 = st1, stratagem2 = st2, stratagem3 = st3, stratagem4 = st4
                )
                database.loadoutDao().insertLoadout(newLoadout)
                Toast.makeText(this@LoadoutActivity, "Loadout Saved!", Toast.LENGTH_SHORT).show()
                binding.etLoadoutName.text.clear()
            }
        }
    }

    private fun loadSavedLoadouts() {
        val userId = sessionManager.getUserId()
        binding.rvLoadouts.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            // Mengambil data secara realtime (Flow)
            database.loadoutDao().getUserLoadouts(userId).collect { loadouts ->
                if (loadouts.isNotEmpty()) {
                    binding.rvLoadouts.adapter = LoadoutAdapter(loadouts)
                    binding.rvLoadouts.visibility = android.view.View.VISIBLE
                } else {
                    // Kosong
                    binding.rvLoadouts.visibility = android.view.View.GONE
                }
            }
        }
    }
}