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

    fun setItemPet(pet: Appointment, position: Int) {
        // USAR POSICIÓN EN LUGAR DE ID PARA EL TURNO
        bindingItem.tvId.text = "#${position + 1}"

        bindingItem.tvName.text = pet.nombreMascota
        bindingItem.tvSymptom.text = pet.sintomas
        Glide.with(bindingItem.ivPicture.context)
            .load(pet.foto)
            .circleCrop()
            .placeholder(R.drawable.cory)
            .into(bindingItem.ivPicture)

        bindingItem.cvPets.setOnClickListener{
            val bundle = Bundle().apply {
                putInt("appointmentId", pet.id)
                putInt("appointmentPosition", position + 1) // PASAR POSICIÓN TAMBIÉN
            }
            navController.navigate(R.id.action_fragmentHome2_to_detalle_cita, bundle)
        }
    }
}