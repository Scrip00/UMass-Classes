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

	private val _coursesLiveData = MutableLiveData<Resource<UMassResponse>>()
	val coursesLiveData: LiveData<Resource<UMassResponse>> = _coursesLiveData

	init {
		fetchUMassCourses()
	}

	private fun fetchUMassCourses() {
		_coursesLiveData.postValue(Resource.loading(null))
		viewModelScope.launch {
			_coursesLiveData.postValue(
				Resource.success(
					UMassRepository.getCourses().body()
				)
			)
		}
	}

	fun upsertClassesLocal(classes: List<Result>?) {
		viewModelScope.launch {
			Log.d("LOLLMAO", "LOL")
			classes?.let {
			Log.d("LOLLMAO", classes[0].toString())
			UMassRepository.upsertClassesLocal(classes) }
		}
	}
}