package com.univalle.dogappnew.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.univalle.dogappnew.R
import com.univalle.dogappnew.databinding.FragmentDetalleCitaBinding


class Detalle_cita : Fragment() {

    private lateinit var binding: FragmentDetalleCitaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetalleCitaBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }


}