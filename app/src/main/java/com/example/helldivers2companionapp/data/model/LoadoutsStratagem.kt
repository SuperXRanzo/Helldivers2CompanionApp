package com.example.helldivers2companionapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "loadout_stratagems",
    foreignKeys = [
        ForeignKey(
            entity = Loadout::class,
            parentColumns = ["id"],
            childColumns = ["loadoutId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Stratagem::class,
            parentColumns = ["id"],
            childColumns = ["stratagemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        androidx.room.Index(value = ["loadoutId"]),
        androidx.room.Index(value = ["stratagemId"])
    ]
)
data class LoadoutStratagem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val loadoutId: Int,
    val stratagemId: Int,
    val position: Int // Posisi 1, 2, 3, atau 4
)