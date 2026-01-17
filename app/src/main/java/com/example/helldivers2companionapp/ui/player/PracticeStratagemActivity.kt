package com.example.helldivers2companionapp.ui.player

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helldivers2companionapp.databinding.ActivityPracticeStratagemBinding

class PracticeStratagemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPracticeStratagemBinding

    // Daftar kode stratagem (U: Up, D: Down, L: Left, R: Right)
    private val stratagems = listOf(
        "REINFORCE" to listOf("U", "D", "R", "L", "U"),
        "ORBITAL STRIKE" to listOf("R", "R", "U"),
        "EAGLE AIRSTRIKE" to listOf("U", "R", "D", "R"),
        "SUPPLY PACK" to listOf("D", "L", "D", "U", "U", "R")
    )

    private var currentLevel = 0
    private var inputStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPracticeStratagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadStratagem()

        binding.btnUp.setOnClickListener { checkInput("U") }
        binding.btnDown.setOnClickListener { checkInput("D") }
        binding.btnLeft.setOnClickListener { checkInput("L") }
        binding.btnRight.setOnClickListener { checkInput("R") }
    }

    private fun loadStratagem() {
        inputStep = 0
        val (name, codes) = stratagems[currentLevel]
        binding.tvStratagemName.text = name
        binding.tvSequence.text = codes.joinToString("  ") { it } // Tampilkan "U  D  R"
        binding.tvInputFeedback.text = "Input your sequence..."
    }

    private fun checkInput(input: String) {
        val (_, codes) = stratagems[currentLevel]

        if (input == codes[inputStep]) {
            inputStep++
            binding.tvInputFeedback.text = "✓ OK (${inputStep}/${codes.size})"

            if (inputStep == codes.size) {
                Toast.makeText(this, "STRATAGEM ACTIVATED!", Toast.LENGTH_SHORT).show()
                nextLevel()
            }
        } else {
            inputStep = 0
            binding.tvInputFeedback.text = "❌ FAILED! RESETTING..."
        }
    }

    private fun nextLevel() {
        currentLevel = (currentLevel + 1) % stratagems.size
        loadStratagem()
    }
}