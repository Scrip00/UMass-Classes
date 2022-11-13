package com.scrip0.umassclasses.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrip0.umassclasses.api.UMassAPI.entities.Result
import com.scrip0.umassclasses.api.UMassAPI.entities.UMassResponse
import com.scrip0.umassclasses.other.Resource
import com.scrip0.umassclasses.repositories.UMassRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val UMassRepository: UMassRepository
) : ViewModel() {

	private val _coursesLiveData = MutableLiveData<Resource<MutableList<Result>>>()
	val coursesLiveData: LiveData<Resource<MutableList<Result>>> = _coursesLiveData

	init {
		fetchUMassCourses()
	}

	private fun fetchUMassCourses() {
		_coursesLiveData.postValue(Resource.loading(null))
		viewModelScope.launch {
			var course = UMassRepository.getCourses().body()
			val courses = course?.results
			var page = 1
			while(course?.next != null) {
				page++
				course = UMassRepository.getCoursesFromPage(page.toString()).body()
				course?.results?.let { courses?.addAll(it) }
			}
			_coursesLiveData.postValue(
				Resource.success(
					courses
				)
			)
		}
	}

	fun upsertClassesLocal(classes: List<Result>?) {
		viewModelScope.launch {
			classes?.let {
				UMassRepository.upsertClassesLocal(classes)
			}
		}
	}

	fun getAllResults(query: String) {
		viewModelScope.launch {
			val classes = UMassRepository.getAllClassesLocal()
			val res = classes.map {
				it.description?.let { it1 ->
					UMassRepository.getSimilarity(query, it1).body()
				}
			}
			Log.d("LOLLMAO", res.toString())
		}
	}
}