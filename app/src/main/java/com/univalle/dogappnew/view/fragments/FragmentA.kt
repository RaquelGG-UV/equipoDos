package com.univalle.dogappnew.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.univalle.dogappnew.R
import androidx.navigation.fragment.findNavController



class FragmentA : Fragment() {

    @SuppressLint("SwitchIntDef")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)
        return view
    }

    private fun showBiometricPrompt(){
        val executor = ContextCompat.getMainExecutor(requireContext())
        val biometricPrompt = BiometricPrompt(this,executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    showToast("Autenticacion Exitosa")
                    findNavController().navigate(R.id.action_fragmentA_to_fragmentHome2)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showToast("Error $")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast("Autenticacion fallida")
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticacion Biometrica")
            .setSubtitle("Usa tu huella")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun showToast(message:String){

        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

    }
}


