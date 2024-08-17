package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.database.dao.TaskDAO
import com.example.todo.database.entity.Task
import com.example.todo.database.typeConverters.DateConverter

@Database(
    entities = [Task::class],
    version = 2
)
@TypeConverters(DateConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskDAO(): TaskDAO

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        private const val DATA_BASE_NAME = "Tasks_Database"
        fun getINSTANCE(context: Context): TaskDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    DATA_BASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}