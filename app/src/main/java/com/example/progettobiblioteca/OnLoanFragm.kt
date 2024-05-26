package com.example.progettobiblioteca

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class OnLoanFragm : Fragment() {

    private lateinit var nome: EditText
    private lateinit var effettuaPrestito: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_loan, container, false)
        nome = view.findViewById(R.id.item_name)
        effettuaPrestito = view.findViewById(R.id.loan_button)

        effettuaPrestito.setOnClickListener {
            val objectName = nome.text.toString()
            val context = requireContext()
            val userEmail = getUserEmail(context)
            val itemId = getObjectIdFromName(context, objectName)

            if (itemId != -1) {
                addPrestito(context, userEmail, itemId)
                Toast.makeText(context, "Prestito effettuato con successo", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Oggetto non trovato", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun getObjectIdFromName(context: Context, objectName: String): Int {
        val db = DataBaseHelper(context).readableDatabase

        // Query per cercare l'ID dell'oggetto nella tabella dei film
        val filmQuery = "SELECT $COL_ID FROM $TABLE_NAME_FILM WHERE TitoloFilm = ?"
        val filmCursor = db.rawQuery(filmQuery, arrayOf(objectName))
        if (filmCursor.moveToFirst()) {
            val filmId = filmCursor.getInt(0)
            filmCursor.close()
            return filmId
        }

        // Query per cercare l'ID dell'oggetto nella tabella dei libri
        val libroQuery = "SELECT $COL_ID FROM $TABLE_NAME_BOOKS WHERE TitoloBook = ?"
        val libroCursor = db.rawQuery(libroQuery, arrayOf(objectName))
        if (libroCursor.moveToFirst()) {
            val libroId = libroCursor.getInt(0)
            libroCursor.close()
            return libroId
        }

        // Query per cercare l'ID dell'oggetto nella tabella della musica
        val musicaQuery = "SELECT $COL_ID FROM $TABLE_NAME_CANZONE WHERE TitoloMusica = ?"
        val musicaCursor = db.rawQuery(musicaQuery, arrayOf(objectName))
        if (musicaCursor.moveToFirst()) {
            val musicaId = musicaCursor.getInt(0)
            musicaCursor.close()
            return musicaId
        }

        // Se l'oggetto non è stato trovato in nessuna tabella
        return -1
    }

    private fun addPrestito(context: Context, userEmail: String, item: Int): Long {
        val userId = getUserIdFromEmail(context, userEmail)
        val db = DataBaseHelper(context).writableDatabase
        val currentDate = getCurrentDate()
        val returnDate = getReturnDate(currentDate)
        val values = ContentValues()
        values.put(COL_USER_ID, userId)
        values.put(COL_ITEM_ID, item)
        values.put(COL_DATA_NOLEGGIO, currentDate)
        values.put(COL_DATA_RESTITUZIONE, returnDate)

        return db.insert(TABLE_PRESTITI, null, values)
    }

    private fun getUserEmail(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", "") ?: ""
    }

    private fun getUserIdFromEmail(context: Context, email: String): Int {
        val db = DataBaseHelper(context).readableDatabase
        val query = "SELECT $COL_ID  FROM $TABLE_NAME WHERE Email = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        val userId = if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            -1 // Se l'utente non è stato trovato
        }

        cursor.close()
        return userId
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getReturnDate(currentDate: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = sdf.parse(currentDate)!!
        calendar.add(Calendar.DAY_OF_MONTH, 30) // Esempio: 30 giorni dopo
        return sdf.format(calendar.time)
    }
}
