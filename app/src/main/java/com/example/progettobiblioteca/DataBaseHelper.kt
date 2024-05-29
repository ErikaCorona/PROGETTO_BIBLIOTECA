package com.example.progettobiblioteca

import android.content.Context
import android.util.Log
import android.widget.Switch
import com.example.progettobiblioteca.Notifica.Companion.showNotification
import com.google.firebase.firestore.FirebaseFirestore


class DataBaseHelper(private val context: Context) {
    private val db = FirebaseFirestore.getInstance()
    companion object {
        const val COLLECTION_USERS = "Users"
        const val COLLECTION_BOOKS = "Libri"
        const val COLLECTION_FILM = "Film"
        const val COLLECTION_MUSIC = "Canzone"

        const val COL_EMAIL = "Email"
        const val COL_PASSWORD = "Password"
        const val COL_ADMIN = "Admin"
        const val COL_BOOK_TITOLO = "TitoloBook"
        const val COL_BOOK_AUTORE = "Autore"
        const val COL_ANNO = "Anno"
        const val COL_FILM_TITOLO = "TitoloFilm"
        const val COL_FILM_REGISTA = "Regista"
        const val COL_FILM_ANNO = "AnnoFilm"
        const val COL_MUSICA_TITOLO = "TitoloMusica"
        const val COL_MUSICA_CANTANTE = "Cantante"
        const val COL_MUSICA_ANNO = "AnnoMusica"
        const val COL_MUSICA_GENERE = "Genere"
        }
    // Aggiungi utente a Firestore
    private fun addUser(email: String, password: String, admin: Switch) {
        val user = hashMapOf(
            COL_EMAIL to email,
            COL_PASSWORD to password,
            COL_ADMIN to if (admin.isChecked) 1 else 0
        )

        // Usa `set` con ID univoco, ad esempio email come ID
        db.collection(COLLECTION_USERS).document(email).set(user)
            .addOnSuccessListener {
                showNotification(context, "Successo", "Utente registrato con successo")
            }
            .addOnFailureListener { exception ->
                showNotification(context, "Errore", "Errore nella registrazione: ${exception.message}")
            }
    }


        // Controlla se l'email esiste già
    fun checkEmail(email: String, password: String, admin: Switch) {
        db.collection(COLLECTION_USERS)
            .whereEqualTo(COL_EMAIL, email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    addUser(email, password, admin)
                } else {
                    showNotification(context, "Errore", "Email già presente nel database")
                }
            }
            .addOnFailureListener { exception ->
                showNotification(context, "Errore", "Errore nel controllo email: ${exception.message}")
            }
    }

    // Aggiungi un libro a Firestore
    fun addBook(titolo: String, autore: String, anno: Int? = null) {
        val book = hashMapOf(
            COL_BOOK_TITOLO to titolo,
            COL_BOOK_AUTORE to autore,
            COL_ANNO to anno
        )

        db.collection(COLLECTION_BOOKS).add(book)
            .addOnSuccessListener {documentReference ->
                Log.d("AddFragm", "DocumentSnapshot added with ID: ${documentReference.id}")
                Notifica.showNotification(context, "Successo", "Libro aggiunto con successo")
            }
            .addOnFailureListener { e ->
                Log.w("AddFragm", "Error adding document", e)
                Notifica.showNotification(context, "Errore", "Si è verificato un errore durante l'aggiunta del libro")
            }
    }

    // Aggiungi un film a Firestore
    fun addFilm(titolo: String, regista: String, anno: Int? = null) {
        val film = hashMapOf(
            COL_FILM_TITOLO to titolo,
            COL_FILM_REGISTA to regista,
            COL_FILM_ANNO to anno
        )

        db.collection(COLLECTION_FILM).add(film)
            .addOnSuccessListener {documentReference ->
                Log.d("AddFragm", "DocumentSnapshot added with ID: ${documentReference.id}")
                Notifica.showNotification(context, "Successo", "Film aggiunto con successo")
            }
            .addOnFailureListener { e ->
                Log.w("AddFragm", "Error adding document", e)
                Notifica.showNotification(context, "Errore", "Si è verificato un errore durante l'aggiunta del film")
            }
    }

    // Aggiungi una canzone a Firestore
    fun addMusic(titolo: String, cantante: String, anno: Int? = null, genere: String) {
        val music = hashMapOf(
            COL_MUSICA_TITOLO to titolo,
            COL_MUSICA_CANTANTE to cantante,
            COL_MUSICA_ANNO to anno,
            COL_MUSICA_GENERE to genere
        )

        db.collection(COLLECTION_MUSIC).add(music)
            .addOnSuccessListener { documentReference ->
                Log.d("AddFragm", "DocumentSnapshot added with ID: ${documentReference.id}")
                Notifica.showNotification(context, "Successo", "Musica aggiunta con successo")
            }
            .addOnFailureListener { e ->
                Log.w("AddFragm", "Error adding document", e)
                Notifica.showNotification(context, "Errore", "Si è verificato un errore durante l'aggiunta della musica")
            }
    }
}
