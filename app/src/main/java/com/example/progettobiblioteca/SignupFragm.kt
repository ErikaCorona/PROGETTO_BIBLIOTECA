package com.example.progettobiblioteca

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat
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
            val confirm=confPassEditText.text.toString()


            if (Verifiche.isValidEmail(email) && Verifiche.isValidPassword(password) && Verifiche.confirmPassword(password, confirm)) {

                val myDB = DataBaseHelper(requireContext())
                val trimmedEmail = email.trim()
                val trimmedPassword = password.trim()
                myDB.checkEmail(requireContext(),trimmedEmail, trimmedPassword)

                val intent = Intent(activity, Menu::class.java)
                startActivity(intent)
            } else if (!Verifiche.isValidEmail(email)) {
                val context: Context = requireContext()
                val title = "errore"
                val message = "email non valida"
                Notifica.showNotification(context, title, message)
            } else if (!Verifiche.isValidPassword(password)) {
                val context: Context = requireContext()
                val title = "errore"
                val message = "password errata"
                Notifica.showNotification(context, title, message)
            } else if (Verifiche.isValidPassword(password) && Verifiche.confirmPassword(password, confirm)) {
                val context: Context = requireContext()
                val title = "successo"
                val message = "password corrispondenti"
                Notifica.showNotification(context, title, message)
            } else {
                val context: Context = requireContext()
                val title = "errore"
                val message = "password non corrispondenti"
                Notifica.showNotification(context, title, message)
            }


        }

        return rootView
    }



}
