package com.abc.demo.Network

import com.abc.demo.Models.Api_Response
import com.abc.demo.Models.Films_Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    //People
    @GET("api/people")
    fun getPeople() : Call<Api_Response>

    //Film
    @GET("api/films")
    fun getFilms() : Call<Films_Response>

    companion object {
        var BASE_URL = "https://swapi.dev/"
        fun create() : ApiInterface {
            //Instance
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}