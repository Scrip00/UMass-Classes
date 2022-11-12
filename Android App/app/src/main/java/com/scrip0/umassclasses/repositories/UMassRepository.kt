package com.scrip0.umassclasses.repositories

import com.scrip0.umassclasses.api.RetrofitInstance

class UMassRepository {
	suspend fun getCourses() = RetrofitInstance.api.getCourses()
}