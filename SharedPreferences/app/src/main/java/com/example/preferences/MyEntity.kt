package com.example.preferences

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey @ColumnInfo(name = "student_id")
    val id: Int,
    val name: String
)