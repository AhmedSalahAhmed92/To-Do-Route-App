package com.example.todo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskDAO(): TaskDAO

    companion object {
        private var INSTANCE: TaskDatabase? = null
        private const val DATA_BASE_NAME = "Tasks_Database"
        fun getINSTANCE(context: Context): TaskDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, TaskDatabase::class.java, DATA_BASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}