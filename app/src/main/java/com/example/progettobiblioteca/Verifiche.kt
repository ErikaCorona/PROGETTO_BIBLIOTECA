package com.example.progettobiblioteca
import android.annotation.SuppressLint
import android.util.Patterns.EMAIL_ADDRESS

// classe per le verifiche
class Verifiche {
    companion object {

        fun isValidEmail(email: String): Boolean {
            return EMAIL_ADDRESS.matcher(email).matches()
        }

        @SuppressLint("SuspiciousIndentation")
        fun isValidPassword(password: String): Boolean {
            val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!.?])(?=\\S+\$).{8,}\$"
                return password.matches(passwordRegex.toRegex())
        }
        fun confirmPassword(password: String, confPass: String): Boolean {
            return password == confPass && isValidPassword(confPass)
        }

    }
}