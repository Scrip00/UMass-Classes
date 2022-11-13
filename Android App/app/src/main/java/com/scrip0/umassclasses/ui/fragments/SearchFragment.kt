package com.scrip0.umassclasses.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.scrip0.umassclasses.R
import com.scrip0.umassclasses.other.Status
import com.scrip0.umassclasses.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

	private val viewModel: MainViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		subscribeToObservers()
		setupFilters()

		btnSubmit.setOnClickListener {
			val text = etQuery.text
			viewModel.getAllResults(text.toString())
		}
	}

	private fun setupFilters() {
		viewModel.filterLiveData.observe(viewLifecycleOwner) { result ->
			when (result.status) {
				Status.SUCCESS -> {
					searchProgressBar.isVisible = false
					val subjectFilerArr = mutableListOf<String>()
					val subjectFilerAdapter = ArrayAdapter(
						requireContext(), android.R.layout.simple_spinner_dropdown_item,
						subjectFilerArr
					)
					subjectFiler.adapter = subjectFilerAdapter
					subjectFilerArr.add("All")
					result.data?.classFilter?.let { subjectFilerArr.addAll(it) }
					subjectFilerAdapter.notifyDataSetChanged()
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

	private fun subscribeToObservers() {
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
	}
}