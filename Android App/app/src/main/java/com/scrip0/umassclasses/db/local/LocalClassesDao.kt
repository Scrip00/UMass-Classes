package com.scrip0.umassclasses.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scrip0.umassclasses.api.UMassAPI.entities.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalClassesDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun upsertAllClasses(buildings: List<Result>)

	@Query("SELECT * FROM classes_table")
	suspend fun getAllClasses(): List<Result>
}