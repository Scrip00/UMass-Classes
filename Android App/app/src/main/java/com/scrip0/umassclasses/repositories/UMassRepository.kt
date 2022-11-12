package com.scrip0.umassclasses.repositories

import com.scrip0.umassclasses.api.UMassAPI.RetrofitInstance
import com.scrip0.umassclasses.api.UMassAPI.entities.Result
import com.scrip0.umassclasses.db.local.LocalClassesDao
import javax.inject.Inject

class UMassRepository @Inject constructor(
	private val localClassesDao: LocalClassesDao
){
	suspend fun getCourses() = RetrofitInstance.api.getCourses()

	suspend fun upsertClassesLocal(classes: List<Result>) = localClassesDao.upsertAllClasses(classes)
}