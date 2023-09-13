package com.example.cinemate.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cinemate.R
import com.example.cinemate.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        with(binding) {
            signupButton.setOnClickListener {
                val email = signupEmail.text.toString()
                val password = signupPassword.text.toString()

                if(email.isNotEmpty() && password.isNotEmpty()) {
                    signUp(email,password)
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
                }
            }
            loginRedirectText.setOnClickListener {
                findNavController().navigate(R.id.action_signUptoSignIn)
            }
        }
        return binding.root
    }




    private fun signUp(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            findNavController().navigate(R.id.action_signUpToHome)
        }.addOnFailureListener {
            Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
            //show snackbar (it.message.orEmpty())
        }
    }
}