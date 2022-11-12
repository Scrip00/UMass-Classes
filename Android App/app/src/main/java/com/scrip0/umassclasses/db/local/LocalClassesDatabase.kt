package com.scrip0.umassclasses.db.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.scrip0.umassclasses.api.UMassAPI.entities.Result

@Database(
	entities = [Result::class],
	version = 3
)
@TypeConverters(Converters::class)
abstract class LocalClassesDatabase : RoomDatabase() {
	abstract fun getClassesDao(): LocalClassesDao
}