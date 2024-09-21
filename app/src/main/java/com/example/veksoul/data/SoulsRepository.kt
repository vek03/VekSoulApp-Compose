//Vek Histories

package com.example.veksoul.data

import kotlinx.coroutines.flow.Flow

interface SoulsRepository{
    fun getAllSoulsStream(): Flow<List<Soul>>

    fun getSoulStream(id: Int): Flow<Soul?>

    suspend fun insertSoul(soul: Soul)

    suspend fun deleteSoul(soul: Soul)

    suspend fun updateSoul(soul: Soul)
}