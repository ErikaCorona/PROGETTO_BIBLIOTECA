package com.example.progettobiblioteca

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
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

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_fragment, container, false)
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

        val myDB = DataBaseHelper(requireContext())

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
                        myDB.addBook(titolo, autoreB, annoB)
                    }
                } else {
                    myDB.addBook(titolo, autoreB, null)
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
                        myDB.addFilm(titolo, registaF, annoF)
                    }
                } else {
                    myDB.addFilm(titolo, registaF, null)
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
                        myDB.addMusic(titolo, cantanteM, annoM, genereM)
                    }
                } else {
                    myDB.addMusic(titolo, cantanteM, null, genereM)
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
}