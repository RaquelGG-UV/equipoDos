package com.univalle.dogappnew.view.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.univalle.dogappnew.R
import com.univalle.dogappnew.databinding.FragmentDetalleCitaBinding


class Detalle_cita : Fragment() {

    private lateinit var binding: FragmentDetalleCitaBinding
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetalleCitaBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        //controladores()
        nagivationFragmentDetallecita()
    }

    private fun nagivationFragmentDetallecita(){
        binding.btnEditar.setOnClickListener{
            findNavController().navigate(R.id.action_detalle_cita_to_editar_cita)
        }


    }


}