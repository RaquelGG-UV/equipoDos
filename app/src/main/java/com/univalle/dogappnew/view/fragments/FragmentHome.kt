package com.univalle.dogappnew.view.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.univalle.dogappnew.R
import com.univalle.dogappnew.databinding.FragmentDetalleCitaBinding
import com.univalle.dogappnew.databinding.FragmentHomeBinding
import com.univalle.dogappnew.model.Appointment
import com.univalle.dogappnew.view.adapter.PetsAdapter
import com.univalle.dogappnew.viewmodel.AppointmentViewModel

class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: AppointmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)

        navigationFragmentNewAppointment()
        observerListAppointments()
    }

    private fun navigationFragmentNewAppointment (){
        binding.fabAgregar.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentHome2_to_fragmentNewAppointment)
        }
    }

    private fun observerListAppointments(){
        viewModel.getAllAppointments()
        viewModel.listAppointments.observe(viewLifecycleOwner){
            list ->
            var recycler = binding.rvPets
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = PetsAdapter(list, findNavController())
            recycler.adapter = adapter
            //adapter.notifyDataSetChanged()
        }
    }


}