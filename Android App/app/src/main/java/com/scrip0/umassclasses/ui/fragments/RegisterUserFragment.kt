package com.scrip0.umassclasses.ui.fragments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.scrip0.umassclasses.R
import com.scrip0.umassclasses.other.User
import com.scrip0.umassclasses.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_register_user.*

@AndroidEntryPoint
class RegisterUserFragment : Fragment() {
	private val viewModel: MainViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_register_user, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val mAuth: FirebaseAuth = FirebaseAuth.getInstance();
		val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
//		FirebaseAuth.getInstance().signOut();
		if (currentUser != null) {

			val navOptions = NavOptions.Builder()
				.setPopUpTo(R.id.RegisterUserFragment, true)
				.build()
			findNavController(requireView()).navigate(
				R.id.action_RegisterUserFragment_to_searchFragment,
				savedInstanceState,
				navOptions
			)
		}

		btnForgotPassword.setOnClickListener {

			val email = editTextEmailAddress.text.toString().trim()
			if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches()) || email.isEmpty()) {
				editTextEmailAddress.error = "Please Enter Email To Reset"
				editTextPassword.requestFocus()
				return@setOnClickListener
			}

			mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
				if (task.isSuccessful) {
					Toast.makeText(activity, "Email Sent!", Toast.LENGTH_LONG).show()
				}
			}
		}
		nextPage.setOnClickListener {

			val email = editTextEmailAddress.text.toString().trim()
			val password = editTextPassword.text.toString().trim()

			if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches()) || email.isEmpty()) {
				editTextEmailAddress.error = "Invalid Email"
				editTextPassword.requestFocus()
				return@setOnClickListener
			}
			if (password.isEmpty() || password.length < 6) {
				editTextPassword.error = "Invalid Password"
				editTextPassword.requestFocus()
				return@setOnClickListener
			}


			mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

				if (task.isSuccessful) {
					val navOptions = NavOptions.Builder()
						.setPopUpTo(R.id.RegisterUserFragment, true)
						.build()
					findNavController(it).navigate(
						R.id.action_RegisterUserFragment_to_searchFragment,
						savedInstanceState,
						navOptions
					)
				}


			}
		}

		// button logic
		registerButton.setOnClickListener {
			val email = editTextEmailAddress.text.toString().trim()
			val password = editTextPassword.text.toString().trim()

			if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches()) || email.isEmpty()) {
				editTextEmailAddress.error = "Invalid Email"
				editTextPassword.requestFocus()
				return@setOnClickListener
			}
			if (password.isEmpty() || password.length < 6) {
				editTextPassword.error = "Invalid Password"
				editTextPassword.requestFocus()
				return@setOnClickListener
			}
			Log.d("Registration", "Button Works")
			mAuth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener { task ->
					if (task.isSuccessful) {
						val user = User(email, password)
						Log.d("Registration", user.email)
						FirebaseDatabase.getInstance().getReference("Users")
							.child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(user)
							.addOnCompleteListener { task2 ->
								if (task2.isSuccessful) {
									Toast.makeText(
										activity,
										"User has been Registered Successfully",
										Toast.LENGTH_LONG
									).show()
									val navOptions = NavOptions.Builder()
										.setPopUpTo(R.id.RegisterUserFragment, true)
										.build()
									findNavController(requireView()).navigate(
										R.id.action_RegisterUserFragment_to_searchFragment,
										savedInstanceState,
										navOptions
									)
								} else {
									Toast.makeText(
										activity,
										"Registration Failed, Please Try Again",
										Toast.LENGTH_SHORT
									).show()
								}
							};
					}
				}

		}
	}
}