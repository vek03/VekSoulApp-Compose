//Vek Histories

package com.example.veksoul.data

import kotlinx.coroutines.flow.Flow

class OfflineSoulRepository(private val soulDAO: SoulDAO) : SoulsRepository{
    override fun getAllSoulsStream(): Flow<List<Soul>> = soulDAO.getAllSouls()

    override fun getSoulStream(id: Int): Flow<Soul?> = soulDAO.getSoul(id)

    override suspend fun insertSoul(soul: Soul) = soulDAO.insert(soul)

    override suspend fun deleteSoul(soul: Soul) = soulDAO.delete(soul)

    override suspend fun updateSoul(soul: Soul) = soulDAO.update(soul)
}