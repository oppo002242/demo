package com.abc.demo.Adapters

import android.app.AlertDialog
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abc.demo.Models.ResultsItem
import com.abc.demo.R
import com.abc.demo.Utilities.BasicUtility


class PeopleAdapter(var context: Context, peopleList: MutableList<ResultsItem>) : RecyclerView.Adapter<PeopleAdapter.RecViewHolder>() {
    var people_list = mutableListOf<ResultsItem>()  //People List
    var film_list = mutableListOf<String>()  //Film List

    //Recyclerview
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: FilmAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
   
    init {
        this.context=context;
        this.people_list = peopleList

    }
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_people, parent, false)
        return RecViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        film_list.clear()
        var resultsItem: ResultsItem = people_list.get(position)

        //Name Listener
        holder.name.setOnClickListener {
            film_list.clear()
            for (i in resultsItem.films!!.indices) {
                var start_index=resultsItem.films!![i].toString().indexOf("films/")
                var last_index=resultsItem.films!![i].toString().length-1
                var film_index=resultsItem.films!![i].toString().substring(start_index+6,last_index);
                print("sandeep"+film_index)
                val parsedInt = film_index.toInt()
                println("The parsed int is $parsedInt")
                if(BasicUtility.title_list.size>0){
                    film_list.add(BasicUtility.title_list[parsedInt]!!)
                }
            }
            if(holder.subItem.visibility==View.GONE){
                holder.subItem.visibility=View.VISIBLE
            }else{
                holder.subItem.visibility=View.GONE
            }

        }
        layoutManager  = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)
        recyclerAdapter  = FilmAdapter(context, film_list);
        recyclerView.setAdapter(recyclerAdapter)
        recyclerView.setRecycledViewPool(viewPool)
        holder.bind(resultsItem)
    }

    override fun getItemCount(): Int {
        return people_list.size ?: 0
    }

    inner class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        private val height: TextView
        private val mass: TextView
        var subItem: View

        //Bind View with Data
        fun bind(resultsItem: ResultsItem?) {
            val expanded: Boolean = resultsItem!!.isExpanded
            subItem.visibility = if (expanded) View.VISIBLE else View.GONE
            name.setText(resultsItem.name)
            height.setText( "Height: " + resultsItem.height)
            mass.setText("Mass: " + resultsItem.mass)
        }

        init {
            name = itemView.findViewById(R.id.item_title)
            height = itemView.findViewById(R.id.item_height)
            mass = itemView.findViewById(R.id.item_mass)
            //Title View
            subItem = itemView.findViewById(R.id.sub_item)
            recyclerView=itemView.findViewById(R.id.recview_title);
        }
    }

    public fun abc(resultsItem: ResultsItem) {

    }

}