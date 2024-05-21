package com.example.progettobiblioteca

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var results: List<String> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultText: TextView = itemView.findViewById(R.id.result_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.resultText.text = results[ position]
    }

    override fun getItemCount()= results.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateResults(newResults: List<String>){
        results = newResults
        notifyDataSetChanged()

    }
}