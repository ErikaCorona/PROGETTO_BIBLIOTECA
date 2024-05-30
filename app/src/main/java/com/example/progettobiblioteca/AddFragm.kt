package com.example.progettobiblioteca

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class AddFragm : Fragment() {
    private lateinit var titoloBook: EditText
    private lateinit var autore: EditText
    private lateinit var annoBook: EditText
    private lateinit var titoloFilm: EditText
    private lateinit var regista: EditText
    private lateinit var annoFilm: EditText
    private lateinit var titoloCanzone: EditText
    private lateinit var cantante: EditText
    private lateinit var annoCanzone: EditText
    private lateinit var genere: EditText
    private lateinit var salvaBook: Button
    private lateinit var salvaFilm: Button
    private lateinit var salvaMusic: Button

    private lateinit var db: FirebaseFirestore
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_fragment, container, false)

        db = (requireActivity().application as MyApplication).db

        titoloBook = view.findViewById(R.id.titolo)
        autore = view.findViewById(R.id.autore)
        annoBook = view.findViewById(R.id.anno)
        titoloFilm = view.findViewById(R.id.titoloFilm)
        regista = view.findViewById(R.id.regista)
        annoFilm = view.findViewById(R.id.annoFilm)
        titoloCanzone = view.findViewById(R.id.titoloMusica)
        cantante = view.findViewById(R.id.cantante)
        annoCanzone = view.findViewById(R.id.annoMusica)
        genere = view.findViewById(R.id.genere)
        salvaBook = view.findViewById(R.id.saveBook)
        salvaFilm = view.findViewById(R.id.saveFilm)
        salvaMusic = view.findViewById(R.id.saveMusic)


        salvaBook.setOnClickListener {
            val titolo = titoloBook.text.toString()
            val autoreB = autore.text.toString()
            val annoB = annoBook.text.toString().toIntOrNull()

            if (titolo.isNotBlank() && autoreB.isNotBlank()) {
                if (annoB != null) {
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                    if (annoB > currentYear) {
                        Notifica.showNotification(
                            requireContext(),
                            "Errore",
                            "Anno non può essere nel futuro"
                        )
                    } else {
                        addBook(titolo, autoreB, annoB)
                    }
                } else {
                    addBook(titolo, autoreB, null)
                }
            } else {
                Notifica.showNotification(
                    requireContext(),
                    "Errore",
                    "Titolo e Autore sono obbligatori"
                )
            }
        }

        salvaFilm.setOnClickListener {
            val titolo = titoloFilm.text.toString()
            val registaF = regista.text.toString()
            val annoF = annoFilm.text.toString().toIntOrNull()

            if (titolo.isNotBlank() && registaF.isNotBlank()) {
                if (annoF != null) {
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                    if (annoF > currentYear) {
                        Notifica.showNotification(
                            requireContext(),
                            "Errore",
                            "Anno non può essere nel futuro"
                        )
                    } else {
                        addFilm(titolo, registaF, annoF)
                    }
                } else {
                    addFilm(titolo, registaF, null)
                }
            } else {
                Notifica.showNotification(
                    requireContext(),
                    "Errore",
                    "Titolo e Regista sono obbligatori"
                )
            }
        }

        salvaMusic.setOnClickListener {
            val titolo = titoloCanzone.text.toString()
            val cantanteM = cantante.text.toString()
            val annoM = annoCanzone.text.toString().toIntOrNull()
            val genereM = genere.text.toString()

            if (titolo.isNotBlank() && cantanteM.isNotBlank()) {
                if (annoM != null) {
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                    if (annoM > currentYear) {
                        Notifica.showNotification(
                            requireContext(),
                            "Errore",
                            "Anno non può essere nel futuro"
                        )
                    } else {
                        addMusic(titolo, cantanteM, annoM, genereM)
                    }
                } else {
                    addMusic(titolo, cantanteM, null, genereM)
                }
            } else {
                Notifica.showNotification(
                    requireContext(),
                    "Errore",
                    "Titolo e Cantante sono obbligatori"
                )
            }
        }

        return view
    }

    // Aggiungi un libro a Firestore
    private fun addBook(titolo: String, autore: String, anno: Int? = null) {
        val book = hashMapOf(
            DataBaseHelper.COL_BOOK_TITOLO to titolo,
            DataBaseHelper.COL_BOOK_AUTORE to autore,
            DataBaseHelper.COL_ANNO to anno
        )

        db.collection(DataBaseHelper.COLLECTION_BOOKS).add(book)
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
    private fun addFilm(titolo: String, regista: String, anno: Int? = null) {
        val film = hashMapOf(
            DataBaseHelper.COL_FILM_TITOLO to titolo,
            DataBaseHelper.COL_FILM_REGISTA to regista,
            DataBaseHelper.COL_FILM_ANNO to anno
        )

        db.collection(DataBaseHelper.COLLECTION_FILM).add(film)
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
    private fun addMusic(titolo: String, cantante: String, anno: Int? = null, genere: String) {
        val music = hashMapOf(
            DataBaseHelper.COL_MUSICA_TITOLO to titolo,
            DataBaseHelper.COL_MUSICA_CANTANTE to cantante,
            DataBaseHelper.COL_MUSICA_ANNO to anno,
            DataBaseHelper.COL_MUSICA_GENERE to genere
        )

        db.collection(DataBaseHelper.COLLECTION_MUSIC).add(music)
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