package com.scrip0.umassclasses.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.scrip0.umassclasses.api.UMassAPI.entities.Result

@Dao
interface LocalClassesDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun upsertAllClasses(buildings: List<Result>)
}