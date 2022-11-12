package com.scrip0.umassclasses.other

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

fun View.setMargins(
	left: Int = this.marginLeft,
	top: Int = this.marginTop,
	right: Int = this.marginRight,
	bottom: Int = this.marginBottom,
) {
	layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
		setMargins(left, top, right, bottom)
	}
}

fun Int.toDps(
	view: View?
): Int {
	val resources = view?.resources
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP,
		this.toFloat(),
		resources?.displayMetrics
	).toInt()
}