package com.example.projeto03_comsumo_de_uma_api_pro.data

import com.squareup.moshi.Json

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    @Json(name = "image") val img: String,
    val episode: List<String>//
)

data class Origin(val name: String, val url: String)
data class Location(val name: String, val url: String)