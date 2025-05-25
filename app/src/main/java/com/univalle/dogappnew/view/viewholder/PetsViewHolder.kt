package com.univalle.dogappnew.view.viewholder

import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.univalle.dogappnew.R
import com.univalle.dogappnew.databinding.ItemPetBinding
import com.univalle.dogappnew.model.Appointment

class PetsViewHolder(binding: ItemPetBinding, navController: NavController) : RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController

    fun setItemPet(pet: Appointment) {
        bindingItem.tvId.text = pet.id.toString()
        bindingItem.tvName.text = pet.nombreMascota
        bindingItem.tvSymptom.text = pet.raza

        bindingItem.cvPets.setOnClickListener{
           navController.navigate(R.id.action_detalle_cita_to_fragmentHome2)
        }
    }

}