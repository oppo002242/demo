package com.abc.demo.Adapters

import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abc.demo.R


class FilmAdapter(var context: Context, film_list: MutableList<String>) : RecyclerView.Adapter<FilmAdapter.RecViewHolder>() {
    var title_list = mutableListOf<String>()
    init {
        this.context=context;
        this.title_list = film_list

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return RecViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        val resultsItem = title_list!![position]
        holder.bind(resultsItem)
    }

    override fun getItemCount(): Int {
        return title_list?.size ?: 0
    }

    inner class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var item_title: TextView
         fun bind(resultsItem: String) {
            item_title.setText(resultsItem.toString())

        }
        init {
            item_title = itemView.findViewById(R.id.item_filmtitle)
        }
    }
}