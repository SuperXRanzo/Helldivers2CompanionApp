package com.example.helldivers2companionapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "loadouts",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE // Fitur bagus: User dihapus -> Loadout hilang
        )
    ],
    // Index mempercepat pencarian loadout berdasarkan userId
    indices = [Index(value = ["userId"])]
)
data class Loadout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: Int,          // Relasi ke User
    val name: String,         // Nama Loadout (misal: "Bug Smasher")

    // Data Senjata & Stratagem (Dari codingan atasmu)
    val primaryWeapon: String,
    val secondaryWeapon: String,
    val stratagem1: String,
    val stratagem2: String,
    val stratagem3: String,
    val stratagem4: String,

    // Data Tambahan (Dari codingan bawahmu)
    val isFavorite: Boolean = true, // Default true karena kalau user bikin manual pasti favorit
    val createdAt: Long = System.currentTimeMillis()
)