//Vek Histories

package com.example.veksoul.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SoulDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(soul: Soul)

    @Update
    suspend fun update(soul: Soul)

    @Delete
    suspend fun delete(soul: Soul)

    @Query("SELECT * from souls WHERE id = :id")
    fun getSoul(id: Int): Flow<Soul>

    @Query("SELECT * from souls ORDER BY name ASC")
    fun getAllSouls(): Flow<List<Soul>>

}