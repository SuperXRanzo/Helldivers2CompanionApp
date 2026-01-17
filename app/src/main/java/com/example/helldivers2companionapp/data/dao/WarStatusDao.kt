package com.example.helldivers2companionapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.helldivers2companionapp.data.model.WarStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface WarStatusDao {
    // Dipakai Player untuk update otomatis (Reactive UI)
    @Query("SELECT * FROM war_status ORDER BY liberationProgress DESC")
    fun getAllPlanets(): Flow<List<WarStatus>>

    // Dipakai Player untuk mengecek status terbaru (No Update Yet logic)
    @Query("SELECT * FROM war_status ORDER BY planetName ASC LIMIT 1")
    fun getLatestStatus(): Flow<WarStatus?>

    // Dipakai Player/Admin untuk melihat semua daftar planet
    @Query("SELECT * FROM war_status")
    suspend fun getAllWarStatus(): List<WarStatus>

    @Query("SELECT * FROM war_status WHERE planetName = :planetName LIMIT 1")
    suspend fun getPlanetStatus(planetName: String): WarStatus?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWarStatus(warStatus: WarStatus)

    // Dipakai Admin untuk update persentase liberasi
    @Query("UPDATE war_status SET liberationProgress = :progress WHERE planetName = :name")
    suspend fun updateLiberation(name: String, progress: Float)

    @Update
    suspend fun updateWarStatus(warStatus: WarStatus)
}