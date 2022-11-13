package com.scrip0.umassclasses.ui.fragments

import android.content.Context
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.fragment_search.searchProgressBar

@AndroidEntryPoint
class FilterFragment : Fragment(R.layout.fragment_filter) {

	private val viewModel: MainViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupFilters()

		btnSave.setOnClickListener {
			val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
			with(sharedPref.edit()) {
				putString("filter_class", classesFiler.selectedItem.toString())
				putString("filter_career", careerFiler.selectedItem.toString())
				putString("filter_classN", etClassN.text.toString())
				apply()
			}
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
					classesFiler.adapter = subjectFilerAdapter
					subjectFilerArr.add("All")
					result.data?.classFilter?.let { subjectFilerArr.addAll(it) }
					subjectFilerAdapter.notifyDataSetChanged()

					val careerFilerArr = mutableListOf<String>()
					val careerFilerAdapter = ArrayAdapter(
						requireContext(), android.R.layout.simple_spinner_dropdown_item,
						careerFilerArr
					)
					careerFiler.adapter = careerFilerAdapter
					careerFilerArr.add("All")
					result.data?.careerFilter?.let { careerFilerArr.addAll(it) }
					careerFilerAdapter.notifyDataSetChanged()
					val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@observe
					val filterClass = sharedPref.getString("filter_class", "All")
					val filterCareer = sharedPref.getString("filter_career", "All")
					val filterClassN = sharedPref.getString("filter_classN", "")
					etClassN.setText(filterClassN)
					classesFiler.setSelection(subjectFilerAdapter.getPosition(filterClass))
					careerFiler.setSelection(careerFilerAdapter.getPosition(filterCareer))
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