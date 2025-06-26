package com.univalle.dogappnew.view.fragments

import android.annotation.SuppressLint

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.univalle.dogappnew.R
import androidx.navigation.fragment.findNavController
import com.univalle.dogappnew.databinding.FragmentABinding
import com.univalle.dogappnew.model.UserRequest
import com.univalle.dogappnew.viewmodel.AppointmentViewModel


class FragmentA : Fragment() {

    private lateinit var binding: FragmentABinding
    private lateinit var viewModel: AppointmentViewModel
    private lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("SwitchIntDef")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentABinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", 0)
        setUp()
        viewModelObservers()
    }

    private fun viewModelObservers(){
        observerIsRegister()
    }


    private fun setUp(){
        binding.registrarse.setOnClickListener{
            registerUser()
        }
    }

    private fun registerUser(){
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        val userRequest = UserRequest(email, pass)
        if (email.isNotEmpty() && pass.isNotEmpty()){
            viewModel.registerUser(userRequest)
        }
    }

    private fun observerIsRegister(){
        viewModel.isRegister.observe(viewLifecycleOwner){ userResponse ->
            if (userResponse.isRegister){
                Toast.makeText(context, userResponse.message, Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putString("email", userResponse.email).apply()
                goToHome()

            }else {
                Toast.makeText(context, userResponse.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToHome(){
        findNavController().navigate(R.id.action_fragmentA_to_fragmentHome2)
    }
}


