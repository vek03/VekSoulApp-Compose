//Vek Histories

package com.example.veksoul.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "souls")
data class Soul(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)