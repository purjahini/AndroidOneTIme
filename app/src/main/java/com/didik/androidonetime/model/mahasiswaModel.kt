package com.didik.androidonetime.model

data class mahasiswaModel(
    val api_status: Int,
    val api_message: String,
    val `data`: List<Data>
) {
    data class Data(
        val id: Int,
        val image: String,
        val email: String,
        val visi: String
    )
}