package com.example.progettobiblioteca

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class LoginFragm : Fragment() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_login, container, false)
        emailEditText = view.findViewById(R.id.login_email)
        passwordEditText = view.findViewById(R.id.login_password)
        val loginButton: Button = view.findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(requireContext(), email, password)
                val intent = Intent(activity, Menu::class.java)
                startActivity(intent)

            }
        }
        return view
    }
    private fun login(context: Context, email:String, password:String){
        val dbHelper = DataBaseHelper(context)
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL = ? AND $COL_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        val loggedIn = cursor.count > 0

        cursor.close()
        db.close()
        if(loggedIn){
            isAdmin(requireContext(),email)
            val intent = Intent(activity, Menu::class.java)
            startActivity(intent)
        }else{
            val title = "errore"
            val message = "email o password non valido"
            Notifica.showNotification(context, title, message)
        }
    }
    private fun isAdmin( context: Context,email:String){
        val dbHelper = DataBaseHelper(context)
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL = ? AND $COL_ADMIN = 1"
        val cursor = db.rawQuery(query, arrayOf(email))

        val admin = cursor.count > 0
        cursor.close()
        db.close()
        if(admin){
            val title = "success"
            val message = " sei un admin"
            Notifica.showNotification(context, title, message)
        }else{
            val title = "errore"
            val message = "non sei un admin"
            Notifica.showNotification(context, title, message)
        }
    }
}