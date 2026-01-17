package com.example.helldivers2companionapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val password: String,
    val role: String, // "ADMIN" atau "PLAYER"

    val kills: Int = 0,
    val deaths: Int = 0,
    val missionsCompleted: Int = 0
)