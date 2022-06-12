package com.abc.demo
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abc.demo.Adapters.PeopleAdapter
import com.abc.demo.Models.*
import com.abc.demo.Network.ApiInterface
import com.abc.demo.Utilities.BasicUtility
import com.abc.demo.Utilities.ConnectionDetector
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    //RecyclerView
    lateinit var recyclerView: RecyclerView
    lateinit var spinner_sortby: Spinner
    lateinit var recyclerAdapter: PeopleAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    var peopleList = mutableListOf<ResultsItem>()



    //Adapter
    var isInternetConnection = false
    var cd: ConnectionDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Method to Init View and set Spinner Data
        init_View()

        //Chcek Internet Connection
        cd = ConnectionDetector(this@MainActivity)
        isInternetConnection = cd!!.isConnectingToInternet
        if (!isInternetConnection) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("No Internet Available.")
                .setMessage("Please check your internet connection.")
                .setPositiveButton("OK"
                ) { dialog, id ->
                    // System.exit(1);
                }.create().show()
        } else {
            //Api Call
            get_filmlist()
        }

    }

    //Method to Init View and set Spinner Data
    private fun init_View() {
        recyclerView=findViewById(R.id.recview);
        spinner_sortby=findViewById(R.id.spinner_sortby);
        //Sort by List
        var Sort_List = mutableListOf<String>()
        Sort_List.clear()
        Sort_List.add("Sort By")
        Sort_List.add("Name")
        Sort_List.add("Height")
        Sort_List.add("Mass")
        var Sort_List_Adapter = ArrayAdapter(this, R.layout.simple_spinnr, Sort_List)
        Sort_List_Adapter.setDropDownViewResource(R.layout.spinner_drop_down)
        spinner_sortby.adapter = Sort_List_Adapter
        spinner_sortby.setSelection(0)
        spinner_sortby?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (spinner_sortby.selectedItemPosition == 0) {

                    }else if (spinner_sortby.selectedItemPosition == 1) {
                        try {
                            if (peopleList.size > 0) {
                                Collections.sort(peopleList,
                                    Comparator { o1, o2 ->
                                        o1.name!!.compareTo(o2.name!!)
                                    })
                            }
                            layoutManager = LinearLayoutManager(
                                applicationContext,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            recyclerAdapter.notifyDataSetChanged()

                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }

                    }else if (spinner_sortby.selectedItemPosition == 2) {
                        try {
                            Collections.sort(
                                peopleList,
                                Comparator { o1, o2 ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        return@Comparator java.lang.Double.compare(
                                            o1!!.height!!.toDouble(),
                                            o2!!.height!!.toDouble()
                                        )
                                    }
                                    0
                                })
                            layoutManager = LinearLayoutManager(
                                applicationContext,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            recyclerAdapter.notifyDataSetChanged()

                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }

                    }else if (spinner_sortby.selectedItemPosition == 3) {
                        try {
                            Collections.sort(
                                peopleList,
                                Comparator { o1, o2 ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        return@Comparator java.lang.Double.compare(
                                            o1!!.mass!!.toDouble(),
                                            o2!!.mass!!.toDouble()
                                        )
                                    }
                                    0
                                })
                            layoutManager = LinearLayoutManager(
                                applicationContext,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            recyclerAdapter.notifyDataSetChanged()

                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }

                    }
                }

            }

    }

    //Method to Consume API
    fun get_peoplelist(){
        //Retrofit instance
        val apiInterface = ApiInterface.create().getPeople()
        apiInterface.enqueue( object : Callback<Api_Response> {
            override fun onResponse(call: Call<Api_Response>?, response: Response<Api_Response>?) {
                try {
                    if (response!!.isSuccessful) {
                      //  Log.e("People_Res", Gson().toJson(response.body()))
                        peopleList.clear()
                        if(response.body()!!.count!!>0){
                            if (response.body()!!.count!!>0) {
                                var resultsItem = ResultsItem();


                                for (i in response.body()!!.results?.indices!!) {
                                    resultsItem = ResultsItem(
                                        response.body()!!.results!![i]!!.films,
                                        response.body()!!.results!![i]!!.homeworld,
                                        response.body()!!.results!![i]!!.gender,
                                        response.body()!!.results!![i]!!.skinColor,
                                        response.body()!!.results!![i]!!.edited,
                                        response.body()!!.results!![i]!!.created,
                                        response.body()!!.results!![i]!!.mass,
                                        response.body()!!.results!![i]!!.vehicles,
                                        response.body()!!.results!![i]!!.url,
                                        response.body()!!.results!![i]!!.hairColor,
                                        response.body()!!.results!![i]!!.birthYear,
                                        response.body()!!.results!![i]!!.eyeColor,
                                        response.body()!!.results!![i]!!.species,
                                        response.body()!!.results!![i]!!.starships,
                                        response.body()!!.results!![i]!!.name,
                                        response.body()!!.results!![i]!!.height,
                                        false
                                    )
                                    peopleList.add(resultsItem)
                                    if (peopleList.isEmpty()) {
                                        msg_dialog("No Data Available.")
                                    } else {
                                        //RecyclerView Adapter
                                        layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                                        recyclerView.setLayoutManager(layoutManager)
                                        recyclerAdapter = PeopleAdapter(this@MainActivity, peopleList);
                                        recyclerView.setAdapter(recyclerAdapter)
                                    }
                                    get_filmlist();
                                }
                            }
                        }else{
                            msg_dialog("No Data Available.")
                        }

                    } else {
                        msg_dialog("Something went wrong!! Please try later.")
                    }
                } catch (e: Exception) {
                    print(e.message)
                }
            }
            override fun onFailure(call: Call<Api_Response>?, t: Throwable?) {

            }


        })

    }

    fun get_filmlist() {

        //Retrofit instance
        val apiInterface = ApiInterface.create().getFilms()
        apiInterface.enqueue( object : Callback<Films_Response> {
            override fun onResponse(call: Call<Films_Response>?, response: Response<Films_Response>?) {
                try {
                    if (response!!.isSuccessful) {
                        Log.e("Film_Res", Gson().toJson(response.body()))
                        // title_list.clear()
                        if(response.body()!!.count!!>0){
                            if (response.body()!!.count!!>0) {
                                var resultsItem = ResultsItem_films()
                                for (i in response.body()!!.results?.indices!!) {
                                    BasicUtility.title_list.add(response.body()!!.results!![i]!!.title)



                                }
                                get_peoplelist();




                            }
                        }else{
                            msg_dialog("No Data Available.")
                        }

                    } else {
                        msg_dialog("Something went wrong!! Please try later.")

                    }
                } catch (e: Exception) {
                    print(e.message)
                }
            }

            override fun onFailure(call: Call<Films_Response>?, t: Throwable?) {

            }


        })

    }

    //Message Dialog
    fun msg_dialog(msg: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, which -> dialog.dismiss() }.create().show()
    }

}

