package com.example.githubsearcher.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubsearcher.tapmenu.home.model.GithubModel

@Database(
    entities = [GithubModel::class],
    version = 1,
    exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun githubDao(): GithubDao

    companion object {
        @Volatile
        private var instance: GithubDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): GithubDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDatabase::class.java, "github_database"
                    ).build()
                }
            }
            return instance!!
        }
    }
}