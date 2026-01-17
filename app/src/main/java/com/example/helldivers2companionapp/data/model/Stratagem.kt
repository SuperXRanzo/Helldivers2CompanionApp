package com.example.helldivers2companionapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stratagems")
data class Stratagem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String, // 'OFFENSIVE', 'DEFENSIVE', 'SUPPLY', 'SPECIAL'
    val inputCode: String, // Contoh: "UP, DOWN, LEFT"
    val cooldown: Int, // Detik
    val description: String,
    val damage: Int = 0,
    val uses: Int = 0,
    val iconUrl: String? = null
)