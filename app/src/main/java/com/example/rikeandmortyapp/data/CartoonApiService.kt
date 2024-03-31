package com.example.rikeandmortyapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface CartoonApiService {

    @GET("character")
    fun getCharacters(): Call<CharacterResponse>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Int):Call<Character>

    @GET
    fun getSingleCharacter(@Url url: String): Call<Character>

}