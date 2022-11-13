package com.scrip0.umassclasses.ui.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrip0.umassclasses.api.UMassAPI.entities.Result
import com.scrip0.umassclasses.other.FilterOptions
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

	private val _filterLiveData = MutableLiveData<Resource<FilterOptions>>()
	val filterLiveData: LiveData<Resource<FilterOptions>> = _filterLiveData

	private val _searchLiveData = MutableLiveData<Resource<List<Result>>>()
	val searchLiveData: LiveData<Resource<List<Result>>> = _searchLiveData

	private val _savedLiveData = MutableLiveData<Resource<List<Result>>>()
	val savedLiveData: LiveData<Resource<List<Result>>> = _savedLiveData

	init {
		fetchUMassCourses()
		getFilterOptions()
	}

	private fun fetchUMassCourses() {
		_coursesLiveData.postValue(Resource.loading(null))
		viewModelScope.launch {
			var course = UMassRepository.getCourses().body()
			val courses = course?.results
			var page = 1
			while (course?.next != null) {
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

	@RequiresApi(Build.VERSION_CODES.N)
	fun getAllResults(
		query: String,
		filterClass: String?,
		filterCareer: String?,
		filterClassN: String?
	) {
		viewModelScope.launch {
			_searchLiveData.postValue(Resource.loading(null))
			var classes = UMassRepository.getAllClassesLocal()
			if (!filterClass.equals("All")) {
				classes = classes.filter { it.subject?.id == filterClass }
			}
			if (!filterCareer.equals("All")) {
				classes = classes.filter { it.details?.career == filterCareer }
			}
			if (!filterClassN.equals("") && !filterClassN.isNullOrBlank()) {
				val classesN = filterClassN.split(",")
				val chars = classesN.map { it.replace("\\s".toRegex(), "").toCharArray()[0] }
				classes =
					classes.filter { !it.number.isNullOrEmpty() && chars.contains(it.number!![0]) }
			}
			var res = classes.map {
				SPair(it.description?.let { it1 ->
					UMassRepository.getSimilarity(query, it1).body()?.similarity?.toDouble()
				}, it)
			}
			res = res.filter { it.accuracy != null && it.accuracy >= 0.5 }
			res = res.sortedByDescending { it.accuracy }
			_searchLiveData.postValue(Resource.success(res.map { it.course!! }))
		}
	}

	fun getFilterOptions() = viewModelScope.launch {
		_filterLiveData.postValue(
			Resource.loading(null)
		)
		val classesSet = HashSet<String>()
		val classes = UMassRepository.getAllClassesLocal()
		classesSet.addAll(classes.map {
			it.subject!!.id
		})
		classesSet.remove("")
		val careerSet = HashSet<String>()
		careerSet.addAll(classes.map {
			if (it.details == null) {
				""
			} else {
				it.details!!.career
			}
		})
		careerSet.remove("")
		val list1 = classesSet.toMutableList()
		list1.sort()
		_filterLiveData.postValue(
			Resource.success(
				FilterOptions(list1, careerSet.toMutableList())
			)
		)
	}

	fun addNewPerson() = viewModelScope.launch{
		UMassRepository.addNewPerson()
	}

	fun getClasses() = viewModelScope.launch{
		_savedLiveData.postValue(Resource.loading(null))
		val str = UMassRepository.getClasses()
		var ids = str.split(",");
		ids = ids.map { it.trim()}
		var classes = UMassRepository.getAllClassesLocal()
		classes = classes.filter { ids.contains(it.id) }
		_savedLiveData.postValue(Resource.success(classes))
	}

	fun addClasses(str: String) = viewModelScope.launch{
		UMassRepository.addClasses(str)
	}

	data class SPair(
		val accuracy: Double?,
		val course: Result?
	)
}