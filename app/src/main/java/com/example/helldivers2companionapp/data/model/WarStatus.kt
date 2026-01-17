package com.example.helldivers2companionapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "war_status")
data class WarStatus(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val planetName: String,
    val sector: String,
    val liberationProgress: Float, // 0.0 sampai 100.0
    val playerCount: Int,
    val faction: String // 'TERMINID' atau 'AUTOMATON'
)