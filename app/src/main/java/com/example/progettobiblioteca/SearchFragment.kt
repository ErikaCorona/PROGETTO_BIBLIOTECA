package com.example.progettobiblioteca

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {

    private lateinit var cercaInput: EditText
    private lateinit var cercaResults: RecyclerView
    private lateinit var adapter: CatalogAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        cercaInput = view.findViewById(R.id.search_input)
        cercaResults = view.findViewById(R.id.search_results)

        cercaResults.layoutManager = LinearLayoutManager(context)
        adapter = CatalogAdapter()
        cercaResults.adapter = adapter

        adapter.showCollectionName()

        cercaInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                performSearch(s.toString())
            }
        })

        loadCatalog()

        return view
    }

    private fun loadCatalog() {
        val collections = listOf("Libri", "Film", "Canzone")
        val catalogItems = mutableListOf<CatalogItem>()

        // Carica i libri
        db.collection("Libri")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val title = document.getString("Titolo") ?: ""
                    val author = document.getString("Autore") ?: ""
                    catalogItems.add(CatalogItem("Libri", title, author))
                }

                catalogItems.add(CatalogItem("---------------------------------------------------------------", "", ""))

                loadFilms(catalogItems)
            }
            .addOnFailureListener { exception ->
                // Gestione degli errori durante il caricamento
            }
    }

    private fun loadFilms(catalogItems: MutableList<CatalogItem>) {
        db.collection("Film")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val title = document.getString("Titolo") ?: ""
                    val author = document.getString("Autore") ?: ""
                    catalogItems.add(CatalogItem("Film", title, author))
                }

                catalogItems.add(CatalogItem("---------------------------------------------------------------", "", ""))

                loadMusic(catalogItems)
            }
            .addOnFailureListener { exception ->

            }
    }

    private fun loadMusic(catalogItems: MutableList<CatalogItem>) {
        db.collection("Canzone")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val title = document.getString("Titolo") ?: ""
                    val author = document.getString("Autore") ?: ""
                    catalogItems.add(CatalogItem("Canzone", title, author))
                }
                // Aggiorna l'adattatore con tutti i risultati
                adapter.updateResults(catalogItems)
            }
            .addOnFailureListener { exception ->
                // Gestione degli errori durante il caricamento
            }
    }


    private fun performSearch(query: String) {
        if (query.isEmpty()) {
            adapter.showCollectionName()
            loadCatalog()
        } else {
            adapter.hideCollectionName()
            val filteredCatalogItems = adapter.filterCatalogItems(query)
            adapter.updateResults(filteredCatalogItems)
        }
    }
}
