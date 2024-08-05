package com.example.projeto03_comsumo_de_uma_api_pro.network

import  com.example.projeto03_comsumo_de_uma_api_pro.data.Character
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://rickandmortyapi.com/"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit =
    Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

data class ApiResponse(val info: Info, val results: List<Character>)
data class Info(val count: Int, val pages: Int, val next: String?, val prev: String?)
interface RickAndMortyApiService {
    @GET("api/character")
    suspend fun getCharacters(): ApiResponse
}

object RickAndMortyApi {
    val retrofitService: RickAndMortyApiService by lazy { retrofit.create(RickAndMortyApiService::class.java) }
}