package com.example.helldivers2companionapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.helldivers2companionapp.data.model.Stratagem
import kotlinx.coroutines.flow.Flow

@Dao
interface StratagemDao {
    // Ambil semua data (Flow untuk Realtime UI)
    @Query("SELECT * FROM stratagems ORDER BY name ASC")
    fun getAllStratagems(): Flow<List<Stratagem>>

    // --- TAMBAHKAN INI (PENTING BUAT LOADOUT) ---
    // Fungsi ini mengembalikan List biasa, bukan Flow.
    // Dipakai buat ngisi Spinner di LoadoutActivity.
    @Query("SELECT * FROM stratagems ORDER BY name ASC")
    suspend fun getAllStratagemsList(): List<Stratagem>
    // ---------------------------------------------

    // Untuk fitur Search
    @Query("SELECT * FROM stratagems WHERE name LIKE '%' || :query || '%'")
    fun searchStratagems(query: String): Flow<List<Stratagem>>

    // Filter by Category
    @Query("SELECT * FROM stratagems WHERE category = :category")
    fun getStratagemsByCategory(category: String): Flow<List<Stratagem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStratagem(stratagem: Stratagem)

    @Update
    suspend fun updateStratagem(stratagem: Stratagem)

    @Delete
    suspend fun deleteStratagem(stratagem: Stratagem)
}