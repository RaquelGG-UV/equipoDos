package com.univalle.dogappnew.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.univalle.dogappnew.databinding.FragmentNewAppointmentBinding
import com.univalle.dogappnew.viewmodel.AppointmentViewModel
import com.univalle.dogappnew.model.Appointment

class FragmentNewAppointment : Fragment() {

    private var _binding: FragmentNewAppointmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AppointmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Puedes manejar argumentos aquí si los necesitas
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)

        // Observar la lista de citas en la BD
        viewModel.repository.allAppointments.asLiveData().observe(viewLifecycleOwner) { list ->
            Log.d("FragmentNewAppointment", "Citas en BD: $list")
            Toast.makeText(requireContext(), "Citas en BD: ${list.size}", Toast.LENGTH_SHORT).show()
        }

        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombreMascota.text.toString().trim()
            val raza = binding.atRaza.text.toString().trim()

            if (nombre.isNotEmpty() && raza.isNotEmpty()) {
                val appointment = Appointment(nombreMascota = nombre, raza = raza)
                viewModel.insertAppointment(appointment)
                Toast.makeText(requireContext(), "Guardando cita...", Toast.LENGTH_SHORT).show()
                // Limpiar campos
                binding.etNombreMascota.text?.clear()
                binding.atRaza.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
