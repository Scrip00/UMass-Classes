package com.scrip0.umassclasses.repositories

import com.scrip0.umassclasses.api.TextAPI.TextRetrofitInstance
import com.scrip0.umassclasses.api.UMassAPI.UMassRetrofitInstance
import com.scrip0.umassclasses.api.UMassAPI.entities.Result
import com.scrip0.umassclasses.db.local.LocalClassesDao
import javax.inject.Inject

class UMassRepository @Inject constructor(
	private val localClassesDao: LocalClassesDao
){
	suspend fun getSimilarity(text1: String, text2: String) =
		TextRetrofitInstance.api.getSimilarity(text1, text2)

	suspend fun getCourses() = UMassRetrofitInstance.api.getCourses()

	suspend fun upsertClassesLocal(classes: List<Result>) = localClassesDao.upsertAllClasses(classes)

	suspend fun getAllClassesLocal() = localClassesDao.getAllClasses()
}