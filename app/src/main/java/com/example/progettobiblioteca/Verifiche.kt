package com.example.progettobiblioteca
import android.util.Patterns.EMAIL_ADDRESS
import java.util.regex.Pattern


class Verifiche {
    companion object {

        fun isValidEmail(email: String): Boolean {
            return EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            val passwordPattern = Pattern.compile( "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\\\S+\\\$).{8,}\\\$")
            val matcher=passwordPattern.matcher(password)

            return matcher.matches()
        }
        fun confirmPassword(password: String, confPass: String): Boolean {
            return password == confPass && isValidPassword(confPass)
        }

    }
}