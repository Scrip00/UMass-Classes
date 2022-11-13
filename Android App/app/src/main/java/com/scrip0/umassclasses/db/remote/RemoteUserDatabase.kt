package com.scrip0.umassclasses.db.remote

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.scrip0.umassclasses.other.FireUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteUserDatabase @Inject constructor(
	firestore: FirebaseFirestore
) {
	private val userCollection = firestore.collection("user_collection")

	suspend fun addNewPerson() {
		val user = Firebase.auth.currentUser
		val userQuery = userCollection.whereEqualTo("token", user?.uid).get().await()
		if (userQuery.isEmpty && user?.uid != null) {
			userCollection.add(FireUser(token = user.uid))
		}
	}

	suspend fun getClasses(): String {
		val user = Firebase.auth.currentUser
		val userQuery = userCollection.whereEqualTo("token", user?.uid).get().await()
		if (!userQuery.isEmpty) {
			val doc = userQuery.documents[0]
			val userr = doc.toObject<FireUser>()
			if (userr != null) {
				return userr.classes
			}
		}
		return ""
	}

	suspend fun addClasses(str: String) {
		val user = Firebase.auth.currentUser
		val userQuery = userCollection.whereEqualTo("token", user?.uid).get().await()
		if (!userQuery.isEmpty) {
			val doc = userQuery.documents[0]
			val newUserMap = mutableMapOf<String, Any>()
			newUserMap["classes"] = str
			userCollection.document(doc.id).set(
				newUserMap,
				SetOptions.merge()
			).await()
		}
	}
}