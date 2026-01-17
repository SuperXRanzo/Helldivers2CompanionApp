package com.example.helldivers2companionapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.helldivers2companionapp.data.dao.LoadoutDao
import com.example.helldivers2companionapp.data.dao.MissionDao
import com.example.helldivers2companionapp.data.dao.StratagemDao
import com.example.helldivers2companionapp.data.dao.UserDao
import com.example.helldivers2companionapp.data.dao.WarStatusDao
import com.example.helldivers2companionapp.data.model.Loadout
import com.example.helldivers2companionapp.data.model.LoadoutStratagem
import com.example.helldivers2companionapp.data.model.User
import com.example.helldivers2companionapp.data.model.Stratagem
import com.example.helldivers2companionapp.data.model.WarStatus
import com.example.helldivers2companionapp.data.model.Mission

// Tambahkan class entity ke dalam array entities
@Database(
    entities = [User::class, Stratagem::class, WarStatus::class, Mission::class, Loadout::class, LoadoutStratagem::class],
    version = 3, // Naikkan versi kalau struktur berubah
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun loadoutDao(): LoadoutDao
    abstract fun stratagemDao(): StratagemDao
    abstract fun missionDao(): MissionDao
    abstract fun warStatusDao(): WarStatusDao
    // Nanti tambah stratagemDao() disini kalau sudah dibuat

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "helldivers_db"
                )
                    .fallbackToDestructiveMigration() // Hapus DB lama kalau ada perubahan (aman buat dev)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}