package com.personal.clicker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.personal.clicker.data.dao.ClickerDao
import com.personal.clicker.domain.History

@Database(
    entities = [History::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract val dao: ClickerDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "history_database"
                ).fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}