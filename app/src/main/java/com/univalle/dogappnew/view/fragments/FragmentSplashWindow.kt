package com.univalle.dogappnew.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.univalle.dogappnew.R
import com.univalle.dogappnew.databinding.FragmentSplashWindowBinding

class FragmentSplashWindow : Fragment() {

    private var _binding: FragmentSplashWindowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashWindowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationFragmentHome()
    }

    private fun navigationFragmentHome(){
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_fragmentSplashWindow_to_fragmentA)
        }, 5000)
    }
}