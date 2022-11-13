package com.scrip0.umassclasses.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.scrip0.umassclasses.R
import com.scrip0.umassclasses.api.UMassAPI.entities.Result
import kotlinx.android.synthetic.main.item_search_result.view.*

class SearchResultsAdapter : RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>() {

	private var onItemClickListener: ((Result) -> Unit)? = null

	inner class SearchResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

	private val diffCallback = object : DiffUtil.ItemCallback<Result>() {
		override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
			return oldItem.hashCode() == newItem.hashCode()
		}
	}

	private val differ = AsyncListDiffer(this, diffCallback)

	fun submitList(list: List<Result>) = differ.submitList(list)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
		return SearchResultsViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.item_search_result,
				parent,
				false
			)
		)
	}

	fun setOnItemClickListener(onItemClickListener: (Result) -> (Unit)) {
		this.onItemClickListener = onItemClickListener
	}

	override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
		val course = differ.currentList[position]
		holder.itemView.apply {
			tvName.text = course.id
			tvDescription.text = course.description
			setOnClickListener {
				onItemClickListener?.invoke(course)
			}
		}
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}
}