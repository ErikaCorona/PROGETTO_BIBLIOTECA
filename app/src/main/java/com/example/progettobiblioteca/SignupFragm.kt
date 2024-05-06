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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupFragm : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confPassEditText: EditText
    private lateinit var rootDatabaseRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_signup, container, false)

        emailEditText = rootView.findViewById(R.id.signup_email)
        passwordEditText = rootView.findViewById(R.id.signup_password)
        confPassEditText = rootView.findViewById(R.id.signup_confirm)

        rootDatabaseRef = FirebaseDatabase.getInstance().getReference()

        val registerButton = rootView.findViewById<Button>(R.id.signup_button)
        registerButton.setOnClickListener {
            val email: String = emailEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            val confirm: String = confPassEditText.text.toString()


            if(!Verifiche.isValidEmail(email)) {
            val context: Context = requireContext()
            val title = "Errore"
            val message = "Email non valida"
            showNotification(context, title, message)
        } else if(!Verifiche.isValidPassword(password)) {
                val context: Context = requireContext()
                val title = "Errore"
                val message = "password non valida"
                showNotification(context, title, message)
            }else if (password != confirm) {

                val context: Context = requireContext()
                val title = "Errore"
                val message = "La password e la conferma della password non corrispondono"
                showNotification(context, title, message)
                return@setOnClickListener
            }

            // Effettua la registrazione solo se l'email e la password sono valide
            if (Verifiche.isValidEmail(email) && Verifiche.isValidPassword(password)) {
                // Ottieni un riferimento al nodo "users" nel database
                val usersRef = FirebaseDatabase.getInstance().getReference("users")

                // Crea un nuovo nodo per l'utente
                val userRef = usersRef.push()

                // Salva l'email e la password nel nodo dell'utente
                userRef.child("email").setValue(email)
                userRef.child("password").setValue(password)

                // Mostra una notifica di successo
                val title = "Successo"
                val message = "Registrazione completata con successo"
                showNotification(requireContext(), title, message)

                // Reindirizza l'utente alla schermata del menu
                val intent = Intent(activity, Menu::class.java)
                startActivity(intent)
            } else {
                // Mostra un messaggio di errore se l'email o la password non sono valide
                val context: Context = requireContext()
                val title = "Errore"
                val message = "Email o password non valide"
                showNotification(context, title, message)
            }
        }

        return rootView
    }

    // Funzione per visualizzare una notifica
    private fun showNotification(context: Context, title: String, message: String) {
        val notificationId = 1
        val channelId = "my_channel_id"
        val channelName = "My Channel"

        // Creare un gestore delle notifiche
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Creare un oggetto NotificationCompat.Builder per costruire la notifica
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Mostrare la notifica
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
