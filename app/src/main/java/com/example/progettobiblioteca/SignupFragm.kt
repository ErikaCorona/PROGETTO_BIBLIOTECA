package com.example.progettobiblioteca

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class SignupFragm : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confPassEditText: EditText
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var admin: Switch

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_signup, container, false)

        emailEditText = rootView.findViewById(R.id.signup_email)
        passwordEditText = rootView.findViewById(R.id.signup_password)
        confPassEditText = rootView.findViewById(R.id.signup_confirm)
        admin = rootView.findViewById(R.id.adminSwitch)

        // Inizializza db usando l'istanza fornita da MyApplication
        db = (requireActivity().application as MyApplication).db

        val registerButton = rootView.findViewById<Button>(R.id.signup_button)
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirm = confPassEditText.text.toString()

            if (Verifiche.isValidEmail(email) && Verifiche.isValidPassword(password) && Verifiche.confirmPassword(password, confirm)) {
                val trimmedEmail = email.trim()

                // Controlla se l'email esiste già in Firestore
                db.collection(DataBaseHelper.COLLECTION_USERS).document(trimmedEmail).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            // Email già esistente
                            showNotification("Errore", "Email già esistente")
                        } else {
                            // Crea un nuovo utente in Firestore
                            val user = hashMapOf(
                                DataBaseHelper.COL_EMAIL to email,
                                DataBaseHelper.COL_PASSWORD to password,
                                DataBaseHelper.COL_ADMIN to if (admin.isChecked) 1 else 0
                            )

                            db.collection(DataBaseHelper.COLLECTION_USERS).document(trimmedEmail).set(user)
                                .addOnSuccessListener {
                                    Log.d(TAG, "DocumentSnapshot added with ID: $trimmedEmail")

                                    // Avvia MainActivity
                                    val intent = Intent(context, MainActivity_new::class.java)
                                    startActivity(intent)
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Error adding document", e)
                                    showNotification("Errore", "Si è verificato un errore durante la registrazione. Riprova.")
                                }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error checking document", e)
                        showNotification("Errore", "Si è verificato un errore durante la registrazione. Riprova.")
                    }
            } else if (!Verifiche.isValidEmail(email)) {
                showNotification("Errore", "Email non valida")
            } else if (!Verifiche.isValidPassword(password)) {
                showNotification("Errore", "Password errata")
            } else if (!Verifiche.confirmPassword(password, confirm)) {
                showNotification("Errore", "Password non corrispondenti")
            }
        }

        return rootView
    }

    private fun showNotification(title: String, message: String) {
        val context: Context = requireContext()
        Notifica.showNotification(context, title, message)
    }

    companion object {
        private const val TAG = "SignupFragm"
    }
}
