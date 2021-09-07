package com.example.retrofit

class WikiRepository {
    private val call = ServiceApi.service
    suspend fun hitCountCheck(name: String) = call.userName(name)
}