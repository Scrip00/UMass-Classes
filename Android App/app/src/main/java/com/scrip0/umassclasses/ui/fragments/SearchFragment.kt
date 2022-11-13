package com.scrip0.umassclasses.ui.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.scrip0.umassclasses.R
import com.scrip0.umassclasses.adapters.SearchResultsAdapter
import com.scrip0.umassclasses.other.Status
import com.scrip0.umassclasses.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

	private val viewModel: MainViewModel by viewModels()

	@RequiresApi(Build.VERSION_CODES.N)
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		subscribeToObservers()

		viewModel.addNewPerson()

		btnSaved.setOnClickListener {
			Navigation.findNavController(it).navigate(
				R.id.action_searchFragment_to_savedFragment,
			)
		}

		btnSubmit.setOnClickListener {
			val text = etQuery.text
			val sharedPref =
				activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
			val filterClass = sharedPref.getString("filter_class", "All")
			val filterCareer = sharedPref.getString("filter_career", "All")
			val filterClassN = sharedPref.getString("filter_classN", "")
			viewModel.getAllResults(text.toString(), filterClass, filterCareer, filterClassN)
			Toast.makeText(context, filterClass + filterCareer + filterClassN, Toast.LENGTH_LONG)
				.show()
		}

		btnFilters.setOnClickListener {
			Navigation.findNavController(it).navigate(
				R.id.action_searchFragment_to_filterFragment,
			)
		}
	}


	private fun subscribeToObservers() {
		val searchResultsAdapter = SearchResultsAdapter()
		rvResults.apply {
			adapter = searchResultsAdapter
			layoutManager = LinearLayoutManager(requireContext())
		}

		viewModel.coursesLiveData.observe(viewLifecycleOwner) { result ->
			when (result.status) {
				Status.SUCCESS -> {
//					searchProgressBar.isVisible = false
					viewModel.upsertClassesLocal(result.data)
				}
				Status.ERROR -> {
//					searchProgressBar.isVisible = false
					Toast.makeText(
						context,
						"Cannot load the data: ${result.message}",
						Toast.LENGTH_LONG
					).show()
				}
				Status.LOADING -> {
//					searchProgressBar.isVisible = true
				}
			}
		}

		viewModel.searchLiveData.observe(viewLifecycleOwner) { result ->
			when (result.status) {
				Status.SUCCESS -> {
					searchProgressBar.isVisible = false
					result.data?.let { searchResultsAdapter.submitList(it) }
				}
				Status.ERROR -> {
					searchProgressBar.isVisible = false
					Toast.makeText(
						context,
						"Cannot load the data: ${result.message}",
						Toast.LENGTH_LONG
					).show()
				}
				Status.LOADING -> {
					searchProgressBar.isVisible = true
				}
			}
		}
	}
}