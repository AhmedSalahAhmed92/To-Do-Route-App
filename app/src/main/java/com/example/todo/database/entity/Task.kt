package com.example.todo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "Todo")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var date: Date? = null,
    var time: String? = null,
    var isDone: Boolean? = false,
) : Serializable
