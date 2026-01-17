package com.example.helldivers2companionapp.ui.admin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.helldivers2companionapp.data.database.AppDatabase
import com.example.helldivers2companionapp.data.model.Stratagem
import com.example.helldivers2companionapp.databinding.ActivityManageStratagemsBinding
import kotlinx.coroutines.launch

class ManageStratagemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageStratagemsBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageStratagemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        // Setup Spinner Biasa (Lebih Aman)
        val categories = listOf("OFFENSIVE", "DEFENSIVE", "SUPPLY", "SPECIAL")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter  // Perhatikan ID-nya balik ke 'spinnerCategory'

        binding.btnSave.setOnClickListener {
            saveToDatabase()
        }
    }

    private fun saveToDatabase() {
        val name = binding.etName.text.toString().trim()
        val category = binding.spinnerCategory.selectedItem.toString() // Ambil dari Spinner
        val code = binding.etCode.text.toString().trim()
        val cooldown = binding.etCooldown.text.toString().toIntOrNull() ?: 0
        val damage = binding.etDamage.text.toString().toIntOrNull() ?: 0
        val desc = binding.etDescription.text.toString().trim()

        if (name.isEmpty() || code.isEmpty()) {
            Toast.makeText(this, "Name and Code are required!", Toast.LENGTH_SHORT).show()
            return
        }

        val stratagem = Stratagem(
            name = name,
            category = category,
            inputCode = code,
            description = desc,
            cooldown = cooldown,
            damage = damage,
            uses = -1
        )

        lifecycleScope.launch {
            try {
                database.stratagemDao().insertStratagem(stratagem)
                Toast.makeText(this@ManageStratagemsActivity, "Stratagem Deployed!", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@ManageStratagemsActivity, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}