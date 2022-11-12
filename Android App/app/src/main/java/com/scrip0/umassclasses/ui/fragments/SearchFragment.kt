package com.scrip0.umassclasses.ui.fragments

import android.os.Bundle
import android.view.View
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
	}

	private fun subscribeToObservers() {
		viewModel.coursesLiveData.observe(viewLifecycleOwner) { result ->
			when (result.status) {
				Status.SUCCESS -> {
					searchProgressBar.isVisible = false
//					addAllBuildings(result?.data)
//					setupSearch()
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