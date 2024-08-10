package com.example.todo.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val date: String? = null,
    var isDone: Boolean? = false,
)
