package com.example.helldivers2companionapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "missions")
data class Mission(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val planetName: String,
    val difficulty: Int,
    val type: String,
    val reward: Int,
    val description: String,
    val isActive: Boolean = true
)