package com.univalle.dogappnew.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.univalle.dogappnew.databinding.ItemPetBinding
import com.univalle.dogappnew.model.Appointment
import com.univalle.dogappnew.view.viewholder.PetsViewHolder

class PetsAdapter(private val listPets: MutableList<Appointment>, private val navController: NavController): RecyclerView.Adapter<PetsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        val binding  = ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetsViewHolder(binding, navController)
    }

    override fun getItemCount(): Int {
        return listPets.size
    }

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {
        val pet = listPets[position]
        holder.setItemPet(pet)
    }

}