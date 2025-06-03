package com.univalle.dogappnew.view.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.univalle.dogappnew.R
import com.univalle.dogappnew.databinding.FragmentDetalleCitaBinding
import com.univalle.dogappnew.viewmodel.AppointmentViewModel

class Detalle_cita : Fragment() {

    private lateinit var binding: FragmentDetalleCitaBinding
    private lateinit var calendar: Calendar
    private lateinit var viewModel: AppointmentViewModel
    private var appointmentId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetalleCitaBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()

        appointmentId = arguments?.getInt("appointmentId", -1) ?: -1

        viewModel = ViewModelProvider(this)[AppointmentViewModel::class.java]

        setupToolbar()
        nagivationFragmentDetallecita()
        loadAppointmentData()
    }

    private fun loadAppointmentData() {
        if (appointmentId != -1) {
            viewModel.getAppointmentById(appointmentId)

            viewModel.currentAppointment.observe(viewLifecycleOwner) { appointment ->
                appointment?.let {
                    // MOSTRAR DATOS DINÁMICOS
                    binding.toolbar.title = it.nombreMascota
                    binding.textRaza.text = it.raza
                    binding.textSintoma.text = it.sintomas
                    binding.textPropietario.text = "Propietario: ${it.nombrePropietario}"
                    binding.textTelefono.text = "Teléfono: ${it.telefono}"

                    binding.textTurno.text = "#${it.id}"


                    if (!it.foto.isNullOrEmpty()) {

                        Glide.with(this)
                            .load(it.foto)
                            .placeholder(R.drawable.cory)
                            .error(R.drawable.cory)
                            .into(binding.imagenPerro)
                    } else {

                        binding.imagenPerro.setImageResource(R.drawable.cory)
                    }
                }
            }

        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detalle_cita_to_fragmentHome2)
        }
    }

    private fun nagivationFragmentDetallecita() {
        binding.btnEditar.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("appointmentId", appointmentId)
            }
            findNavController().navigate(R.id.action_detalle_cita_to_editar_cita, bundle)
        }
    }
}