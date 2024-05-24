package com.example.progettobiblioteca
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment


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
                    val intent = Intent(requireActivity(), MenuHandler::class.java)
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
            val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("email", email)
            editor.apply()
        }else{
            val title = "errore"
            val message = "email o password non valido"
            Notifica.showNotification(context, title, message)
        }
    }

    companion object {
        fun isAdmin(context: Context, email:String):Boolean{
        val dbHelper = DataBaseHelper(context)
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL = ? AND $COL_ADMIN = 1"
        val cursor = db.rawQuery(query, arrayOf(email))

        val admin = cursor.count > 0
        cursor.close()
        db.close()

        return admin
    }
}}
