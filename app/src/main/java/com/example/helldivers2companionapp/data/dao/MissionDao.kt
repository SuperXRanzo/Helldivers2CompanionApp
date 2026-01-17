package com.example.helldivers2companionapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.helldivers2companionapp.data.model.Mission
import kotlinx.coroutines.flow.Flow

@Dao
interface MissionDao {
    @Query("SELECT * FROM missions WHERE isActive = 1 ORDER BY difficulty ASC")
    fun getAllActiveMissions(): Flow<List<Mission>>

    @Query("SELECT * FROM missions ORDER BY planetName ASC")
    fun getAllMissionsForAdmin(): Flow<List<Mission>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMission(mission: Mission)

    @Update
    suspend fun updateMission(mission: Mission)

    @Delete
    suspend fun deleteMission(mission: Mission)
}