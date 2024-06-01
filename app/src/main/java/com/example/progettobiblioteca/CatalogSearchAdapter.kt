package com.example.progettobiblioteca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CatalogAdapter : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    private var catalogItems: MutableList<CatalogItem> = mutableListOf()
    private var filteredCatalogItems: MutableList<CatalogItem> = mutableListOf()
    private var collectionNameVisible: Boolean = false

    fun showCollectionName() {
        collectionNameVisible = true
        notifyDataSetChanged()
    }

    fun hideCollectionName() {
        collectionNameVisible = false
        notifyDataSetChanged()
    }

    fun updateResults(catalogItems: List<CatalogItem>) {
        this.catalogItems.clear()
        this.catalogItems.addAll(catalogItems)
        this.filteredCatalogItems.clear()
        this.filteredCatalogItems.addAll(catalogItems)
        notifyDataSetChanged()
    }

    fun filterCatalogItems(query: String): List<CatalogItem> {
        val filteredList = mutableListOf<CatalogItem>()
        for (item in catalogItems) {
            if (item.title.contains(query, ignoreCase = true)) {
                filteredList.add(item)
            }
        }
        return filteredList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return CatalogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val currentItem = filteredCatalogItems[position]
        holder.titleTextView.text = currentItem.title
        holder.authorTextView.text = currentItem.author
        if (collectionNameVisible) {
            holder.collectionNameTextView.visibility = View.VISIBLE
            holder.collectionNameTextView.text = currentItem.collectionName
        } else {
            holder.collectionNameTextView.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return filteredCatalogItems.size
    }

    class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val collectionNameTextView: TextView = itemView.findViewById(R.id.result_collection_name)
        val titleTextView: TextView = itemView.findViewById(R.id.result_text)
        val authorTextView: TextView = itemView.findViewById(R.id.result_author)
    }
}

