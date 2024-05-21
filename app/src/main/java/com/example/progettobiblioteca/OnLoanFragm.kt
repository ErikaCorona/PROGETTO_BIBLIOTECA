package com.example.progettobiblioteca

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class OnLoanFragm : Fragment() {

    private lateinit var nome: EditText
    private lateinit var effetuaPrestito: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_loan,container,false)
        nome=view.findViewById(R.id.item_name)
        val salvaPrestito=view.findViewById<Button>(R.id.loan_button)
        salvaPrestito.setOnClickListener{{}
    }
        return view
    }
}