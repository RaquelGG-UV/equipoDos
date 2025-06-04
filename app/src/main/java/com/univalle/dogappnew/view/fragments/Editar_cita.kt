package com.univalle.dogappnew.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.univalle.dogappnew.R
import com.univalle.dogappnew.databinding.FragmentEditarCitaBinding
import com.univalle.dogappnew.model.Appointment
import com.univalle.dogappnew.viewmodel.AppointmentViewModel

class Editar_cita : Fragment() {

    private lateinit var binding: FragmentEditarCitaBinding
    private lateinit var viewModel: AppointmentViewModel
    private var appointmentId: Int = -1
    private var currentAppointment: Appointment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditarCitaBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appointmentId = arguments?.getInt("appointmentId", -1) ?: -1
        viewModel = ViewModelProvider(this)[AppointmentViewModel::class.java]

        setupToolbar()
        controladores()
        observadorViewModel()
        loadAppointmentData()
        setupRazasDropdown()
    }

    private fun loadAppointmentData() {
        if (appointmentId != -1) {
            viewModel.getAppointmentById(appointmentId)
        }
    }

    private fun setupRazasDropdown() {
        viewModel.getRazas()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupValidations() {
        val validateFields = {
            currentAppointment?.let { appointment ->
                val nombre = binding.etNombreMascota.text.toString().trim()
                val raza = binding.autoCompleteRaza.text.toString().trim()
                val propietario = binding.etPropietario.text.toString().trim()
                val telefono = binding.etTelefono.text.toString().trim()

                // Verificar si todos los campos están llenos
                val allFieldsFilled = nombre.isNotEmpty() && raza.isNotEmpty() &&
                        propietario.isNotEmpty() && telefono.isNotEmpty()

                // Verificar si hay cambios reales
                val hasChanges = nombre != appointment.nombreMascota ||
                        raza != appointment.raza ||
                        propietario != appointment.nombrePropietario ||
                        telefono != appointment.telefono

                // Habilitar botón solo si hay cambios Y todos los campos están llenos
                val shouldEnable = allFieldsFilled && hasChanges
                binding.btnEditarCita.isEnabled = shouldEnable

                // Cambiar color del texto
                if (shouldEnable) {
                    binding.btnEditarCita.setTextColor(resources.getColor(android.R.color.white, null))
                    binding.btnEditarCita.setTypeface(null, android.graphics.Typeface.BOLD)
                } else {
                    binding.btnEditarCita.setTextColor(resources.getColor(android.R.color.black, null))
                    binding.btnEditarCita.setTypeface(null, android.graphics.Typeface.NORMAL)
                }
            }
        }

        // Escuchar cambios en los campos
        binding.etNombreMascota.addTextChangedListener { validateFields() }
        binding.autoCompleteRaza.addTextChangedListener { validateFields() }
        binding.etPropietario.addTextChangedListener { validateFields() }
        binding.etTelefono.addTextChangedListener { validateFields() }
    }

    private fun controladores() {
        binding.btnEditarCita.setOnClickListener {
            saveChanges()
        }
    }

    private fun saveChanges() {
        val nombreMascota = binding.etNombreMascota.text.toString().trim()
        val raza = binding.autoCompleteRaza.text.toString().trim()
        val propietario = binding.etPropietario.text.toString().trim()
        val telefono = binding.etTelefono.text.toString().trim()

        if (nombreMascota.isEmpty()) {
            binding.tiNombreMascota.error = "Ingrese el nombre de la mascota"
            return
        }
        if (raza.isEmpty()) {
            binding.tiRaza.error = "Seleccione una raza"
            return
        }
        if (propietario.isEmpty()) {
            binding.tiPropietario.error = "Ingrese el nombre del propietario"
            return
        }
        if (telefono.isEmpty()) {
            binding.tiTelefono.error = "Ingrese el teléfono"
            return
        }

        currentAppointment?.let { appointment ->
            // Obtener nueva imagen para la raza
            viewModel.getImageByBreed(raza) { imageUrl ->
                val updatedAppointment = appointment.copy(
                    nombreMascota = nombreMascota,
                    raza = raza,
                    nombrePropietario = propietario,
                    telefono = telefono,
                    foto = imageUrl
                )

                viewModel.updateAppointment(updatedAppointment)
                Toast.makeText(requireContext(), "Cita actualizada", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_editar_cita_to_fragmentHome2)
            }
        }
    }

    private fun observadorViewModel() {
        viewModel.currentAppointment.observe(viewLifecycleOwner) { appointment ->
            appointment?.let {
                currentAppointment = it
                binding.etNombreMascota.setText(it.nombreMascota)
                binding.autoCompleteRaza.setText(it.raza)
                binding.etPropietario.setText(it.nombrePropietario)
                binding.etTelefono.setText(it.telefono)

                // ✅ CONFIGURAR VALIDACIONES DESPUÉS DE CARGAR LOS DATOS
                setupValidations()
            }
        }

        viewModel.listRazas.observe(viewLifecycleOwner) { razas ->
            if (razas.isNotEmpty()) {
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    razas
                )
                binding.autoCompleteRaza.setAdapter(adapter)
            }
        }
    }
}