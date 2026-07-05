package com.omnix.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omnix.core.data.local.entity.ModelStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ModelStateDao {

    @Query("SELECT * FROM model_states")
    fun observeAll(): Flow<List<ModelStateEntity>>

    @Query("SELECT * FROM model_states WHERE modelId = :modelId LIMIT 1")
    suspend fun getById(modelId: String): ModelStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ModelStateEntity)

    @Query("DELETE FROM model_states WHERE modelId = :modelId")
    suspend fun delete(modelId: String)

    @Query("UPDATE model_states SET isActive = 0")
    suspend fun clearActive()

    @Query("UPDATE model_states SET isActive = 1 WHERE modelId = :modelId")
    suspend fun setActive(modelId: String)
}
