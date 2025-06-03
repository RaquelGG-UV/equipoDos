package com.univalle.dogappnew.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.univalle.dogappnew.R
import com.univalle.dogappnew.databinding.FragmentNewAppointmentBinding
import com.univalle.dogappnew.viewmodel.AppointmentViewModel
import com.univalle.dogappnew.model.Appointment

class FragmentNewAppointment : Fragment() {

    private var _binding: FragmentNewAppointmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AppointmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        autoComplateTextView()
        listsymptoms()
        saveAppointment()
        goBack()
    }

    private fun autoComplateTextView(){
        viewModel.getRazas()
        viewModel.listRazas.observe(viewLifecycleOwner){razas ->
            val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, razas)
            binding.atRaza.setAdapter(adapter)
        }
    }

    private fun listsymptoms(){
        val listsymptoms: Array<String> = resources.getStringArray(R.array.symptoms)
        val defaultVal = listsymptoms.get(0)
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listsymptoms)
        binding.atSintomas.setAdapter(adapter)
        binding.atSintomas.setText(defaultVal,false)
    }

    private fun saveAppointment(){

        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombreMascota.text.toString().trim()
            val raza = binding.atRaza.text.toString().trim()
            val nombrePropietarioAp = binding.etNombrePropietario.text.toString().trim()
            val telefonoPropietario = binding.etTelefono.text.toString().trim()
            val sintomasMascota = binding.atSintomas.text.toString().trim()


                if (nombre.isNotEmpty() && raza.isNotEmpty() && nombrePropietarioAp.isNotEmpty() && telefonoPropietario.isNotEmpty() && sintomasMascota.isNotEmpty()) {
//                val appointment = Appointment(nombreMascota = nombre, raza = raza, nombrePropietario = nombrePropietarioAp, telefono = telefonoPropietario, sintomas = sintomasMascota)
//                viewModel.insertAppointment(appointment)
//                Toast.makeText(requireContext(), "Guardando cita...", Toast.LENGTH_SHORT).show()

                    viewModel.getPicture(raza)

                    viewModel.listPictures.observe(viewLifecycleOwner) { imageUrl ->
                        val appointment = Appointment(
                            nombreMascota = nombre,
                            raza = raza,
                            nombrePropietario = nombrePropietarioAp,
                            telefono = telefonoPropietario,
                            sintomas = sintomasMascota,
                            foto = imageUrl
                        )
                        viewModel.insertAppointment(appointment)
                        Toast.makeText(requireContext(), "Guardando cita...", Toast.LENGTH_SHORT).show()
                    }
                    
                binding.etNombreMascota.text?.clear()
                binding.atRaza.text?.clear()
                binding.etNombrePropietario.text?.clear()
                binding.etTelefono.text?.clear()

                // Volver a establecer el valor por defecto en síntomas
                val defaultSymptom = resources.getStringArray(R.array.symptoms)[0]
                binding.atSintomas.setText(defaultSymptom, false)
            } else {
                Toast.makeText(requireContext(), "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goBack(){
        binding.toolbarNewAppointment.backArrow.setOnClickListener{
            //requireActivity().onBackPressed()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
