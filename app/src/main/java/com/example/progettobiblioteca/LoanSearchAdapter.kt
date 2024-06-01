package com.example.progettobiblioteca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class LoanSearchAdapter : RecyclerView.Adapter<LoanSearchAdapter.ViewHolder>() {

    private val loanItems = mutableListOf<LoanItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_loan_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = loanItems[position]
        holder.resultCollectionName.text = item.collectionName
        holder.resultText.text = item.title
        holder.resultReturnDate.text = item.returnDate
    }

    override fun getItemCount(): Int {
        return loanItems.size
    }

    fun updateResults(newItems: List<LoanItem>) {
        loanItems.clear()
        loanItems.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultCollectionName: TextView = itemView.findViewById(R.id.result_collection_name)
        val resultText: TextView = itemView.findViewById(R.id.result_text)
        val resultReturnDate: TextView = itemView.findViewById(R.id.result_return_date)
    }
}
