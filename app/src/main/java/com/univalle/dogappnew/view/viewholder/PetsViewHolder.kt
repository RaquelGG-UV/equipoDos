package com.univalle.dogappnew.view.viewholder

import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.univalle.dogappnew.R
import com.univalle.dogappnew.databinding.ItemPetBinding
import com.univalle.dogappnew.model.Appointment
import android.os.Bundle

class PetsViewHolder(binding: ItemPetBinding, navController: NavController) : RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController

    fun setItemPet(pet: Appointment) {
        bindingItem.tvId.text = pet.id.toString()
        bindingItem.tvName.text = pet.nombreMascota
        bindingItem.tvSymptom.text = pet.sintomas
        Glide.with(bindingItem.ivPicture.context)
            .load(pet.foto)
            .circleCrop() // Si quieres que sea circular
            .placeholder(R.drawable.cory)
            .into(bindingItem.ivPicture)

        bindingItem.cvPets.setOnClickListener{
            val bundle = Bundle().apply {
                putInt("appointmentId", pet.id)
            }
            navController.navigate(R.id.action_fragmentHome2_to_detalle_cita, bundle)
        }
    }

}