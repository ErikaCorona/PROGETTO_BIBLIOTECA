package com.example.progettobiblioteca

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class SignupFragm : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confPassEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_signup, container, false)


        emailEditText = rootView.findViewById(R.id.signup_email)
        passwordEditText = rootView.findViewById(R.id.signup_password)
        confPassEditText = rootView.findViewById(R.id.signup_confirm)


        val registerButton = rootView.findViewById<Button>(R.id.signup_button)
        registerButton.setOnClickListener {

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirm = confPassEditText.text.toString()


            if (Verifiche.isValidEmail(email)) {
                println("email valida")
            } else {
                println("email non valida")
            }

            if (Verifiche.isValidPassword(password)) {
                println("password  valida")
            } else {
                println("password non valida")
            }
            if (Verifiche.confirmPassword(password, confirm)) {
                println("password confermata")
            } else {
                println("password non corrispondenti")
            }
            if (Verifiche.isValidEmail(email) && Verifiche.isValidPassword(password) && Verifiche.confirmPassword(password,confirm)) {
                println("Registrazione riuscita")
                onRegistrationSuccess()
            } else {
                println("Registrazione fallita")
            }

        }

        return rootView
    }

    fun onRegistrationSuccess() {
        val intent = Intent(activity, MainActivity_secondaschermatamenulaterale::class.java)
        startActivity(intent)
    }
}
