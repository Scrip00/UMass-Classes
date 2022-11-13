package com.scrip0.umassclasses.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.scrip0.umassclasses.R
import com.scrip0.umassclasses.adapters.SearchResultsAdapter
import com.scrip0.umassclasses.other.Status
import com.scrip0.umassclasses.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.searchProgressBar

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.fragment_saved) {

	private val viewModel: MainViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.getClasses()

		val searchResultsAdapter = SearchResultsAdapter()
		rvSaved.apply {
			adapter = searchResultsAdapter
			layoutManager = LinearLayoutManager(requireContext())
		}

		viewModel.savedLiveData.observe(viewLifecycleOwner) { result ->
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