package com.example.helldivers2companionapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.helldivers2companionapp.data.model.Loadout
import kotlinx.coroutines.flow.Flow

@Dao
interface LoadoutDao {
    // 1. Simpan Loadout Baru (Otomatis simpan senjata & stratagem karena sudah satu tabel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoadout(loadout: Loadout): Long

    // 2. Ambil Loadout User (Real-time dengan Flow)
    // Urutkan: Favorit dulu, baru yang paling baru dibuat
    @Query("SELECT * FROM loadouts WHERE userId = :userId ORDER BY isFavorite DESC, createdAt DESC")
    fun getUserLoadouts(userId: Int): Flow<List<Loadout>>

    // 3. Hapus Loadout
    @Query("DELETE FROM loadouts WHERE id = :id")
    suspend fun deleteLoadout(id: Int)

    // 4. Update Status Favorite (Opsional jika mau fitur klik love/bintang)
    @Query("UPDATE loadouts SET isFavorite = :isFav WHERE id = :loadoutId")
    suspend fun updateFavoriteStatus(loadoutId: Int, isFav: Boolean)
}