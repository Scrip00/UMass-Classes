package com.scrip0.umassclasses.db.local

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scrip0.umassclasses.api.UMassAPI.entities.Details
import com.scrip0.umassclasses.api.UMassAPI.entities.EnrollmentInformation
import com.scrip0.umassclasses.api.UMassAPI.entities.Offering
import com.scrip0.umassclasses.api.UMassAPI.entities.Subject

class Converters {
	private val gson = Gson()

	@TypeConverter
	fun detailsToString(details: Details?): String {
		return gson.toJson(details)
	}

	@TypeConverter
	fun stringToDetails(string: String?): Details {
		val objectType = object : TypeToken<Details>() {}.type
		string?.let { return Details() }
        return gson.fromJson(string, objectType)
	}

	@TypeConverter
	fun enrollmentInformationToString(info: EnrollmentInformation?): String {
		return gson.toJson(info)
	}

	@TypeConverter
	fun stringToEnrollmentInformation(string: String?): EnrollmentInformation {
		val objectType = object : TypeToken<EnrollmentInformation>() {}.type
		string?.let { return EnrollmentInformation() }
        return gson.fromJson(string, objectType)
	}

	@TypeConverter
	fun offeringToString(offering: List<Offering>?): String {
		return gson.toJson(offering)
	}

	@TypeConverter
	fun stringToOffering(string: String?): List<Offering> {
		val objectType = object : TypeToken<Offering>() {}.type
		string?.let { return emptyList() }
        return gson.fromJson(string, objectType)
	}

	@TypeConverter
	fun subjectToString(subject: Subject?): String {
		return gson.toJson(subject)
	}

	@TypeConverter
	fun stringToSubject(string: String?): Subject {
		val objectType = object : TypeToken<Subject>() {}.type
		string?.let { return Subject() }
        return gson.fromJson(string, objectType)
	}
}