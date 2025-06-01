package com.univalle.dogappnew.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.univalle.dogappnew.R
import com.univalle.dogappnew.databinding.FragmentEditarCitaBinding

class Editar_cita : Fragment() {

    private lateinit var binding: FragmentEditarCitaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using data binding
        binding = FragmentEditarCitaBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        controladores()
        observadorViewModel()
    }

    private fun setupToolbar() {
        // Botón de regreso en el toolbar - va al home
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_editar_cita_to_detalle_cita)
        }
    }

    private fun controladores() {
        // Botón editar cita - regresa al detalle
        binding.btnEditarCita.setOnClickListener {
            findNavController().navigate(R.id.action_editar_cita_to_detalle_cita)
        }
    }

    private fun observadorViewModel() {
        // Aquí puedes implementar observadores de LiveData si usas ViewModel
    }
}