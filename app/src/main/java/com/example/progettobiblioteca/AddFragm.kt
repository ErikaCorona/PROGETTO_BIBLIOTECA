package com.example.progettobiblioteca

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

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
        val view = inflater.inflate(R.layout.add_fragment,container,false)
        titoloBook=view.findViewById(R.id.titolo)
        autore=view.findViewById(R.id.autore)
        annoBook=view.findViewById(R.id.anno)
        titoloFilm=view.findViewById(R.id.titoloFilm)
        regista=view.findViewById(R.id.regista)
        annoFilm=view.findViewById(R.id.annoFilm)
        titoloCanzone=view.findViewById(R.id.titoloMusica)
        cantante=view.findViewById(R.id.cantante)
        annoCanzone=view.findViewById(R.id.annoMusica)
        genere=view.findViewById(R.id.genere)
        val salvaBook = view.findViewById<Button>(R.id.saveBook)

        salvaBook.setOnClickListener{
            val myDB = DataBaseHelper(requireContext())
            val titolo=titoloBook.text.toString()
            val autoreB=autore.text.toString()
            val annoB=Integer.parseInt(annoBook.text.toString())
            myDB.addBook(requireContext(),titolo,autoreB,annoB)
        }
        return view
    }




}