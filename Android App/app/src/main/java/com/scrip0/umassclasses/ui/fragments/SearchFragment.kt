package com.scrip0.umassclasses.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
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

		btnSubmit.setOnClickListener {
			val text = etQuery.text
//			viewModel.getAllResults(text.toString())
			val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
			val filterClass = sharedPref.getString("filter_class", "All")
			val filterCareer = sharedPref.getString("filter_career", "All")
			val filterClassN = sharedPref.getString("filter_classN", "")
			Toast.makeText(context, filterClass + filterCareer + filterClassN, Toast.LENGTH_LONG).show()
		}

		btnFilters.setOnClickListener {
			Navigation.findNavController(it).navigate(
				R.id.action_searchFragment_to_filterFragment,
			)
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