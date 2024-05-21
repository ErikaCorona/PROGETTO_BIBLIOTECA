package com.example.progettobiblioteca

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchFragment : Fragment() {

    private lateinit var cercaInput: EditText
    private lateinit var cercaButton: Button
    private lateinit var cercaResults: RecyclerView
    private lateinit var adapter:SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search,container,false)
        cercaInput = view.findViewById(R.id.search_input)
        cercaButton = view.findViewById(R.id.search_button)
        cercaResults = view.findViewById(R.id.search_results)

        cercaResults.layoutManager=LinearLayoutManager(context)
        adapter=SearchAdapter()
        cercaResults.adapter= adapter

        cercaButton.setOnClickListener{
            performSearch(cercaInput.text.toString())
        }
        return view
    }

    private fun performSearch ( query : String){

    }
}