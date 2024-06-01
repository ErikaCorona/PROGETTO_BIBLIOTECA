package com.example.progettobiblioteca

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


    class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

        private var loanItems: List<LoanItem> = emptyList()

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val resultCollectionName: TextView = itemView.findViewById(R.id.result_collection_name)
            val resultText: TextView = itemView.findViewById(R.id.result_text)
            val resultReturnDate: TextView = itemView.findViewById(R.id.result_return_date)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = loanItems[position]
            holder.resultCollectionName.text = item.collectionName // Imposta il nome della collezione
            holder.resultText.text = item.title
            holder.resultReturnDate.text = "Data restituzione: ${item.returnDate}"
        }

        override fun getItemCount() = loanItems.size

        @SuppressLint("NotifyDataSetChanged")
        fun updateResults(newLoanItems: List<LoanItem>) {
            loanItems = newLoanItems
            notifyDataSetChanged()
        }
}
