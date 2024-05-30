package com.example.progettobiblioteca

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.progettobiblioteca.DataBaseHelper.Companion.COLLECTION_USERS
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragm : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Ottieni l'istanza di Firestore da MyApplication
        db = (requireActivity().application as MyApplication).db

        emailEditText = view.findViewById(R.id.login_email)
        passwordEditText = view.findViewById(R.id.login_password)
        val loginButton: Button = view.findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Inserisci email e password", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun loginUser(email: String, password: String) {
        db.collection("Users")
            .whereEqualTo("Email", email)
            .whereEqualTo("Password", password)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {

                    val sharedPreferences = context?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putString("email", email)
                    editor?.apply()

                    val intent = Intent(requireActivity(), MenuHandler::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Email o password non validi", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Errore: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    object AdminManager {
        fun isAdmin(email: String, callback: (Boolean) -> Unit) {
            // Usa l'istanza di Firestore da MyApplication
            val db = (MyApplication.getInstance()).db

            db.collection(COLLECTION_USERS)
                .document(email)
                .get()
                .addOnSuccessListener { document ->
                    val isAdmin = document.exists()
                    callback(isAdmin)
                }
                .addOnFailureListener {
                    // Gestione degli errori
                    callback(false)
                }
        }
    }
}


