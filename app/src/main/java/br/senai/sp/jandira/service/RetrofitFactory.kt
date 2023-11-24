package br.senai.sp.jandira.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    val baseURL = "http://10.107.144.3:3000/"

    private var retrofitFactory = Retrofit
        .Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getSignUp() : SignUpService{
        return retrofitFactory.create(SignUpService::class.java)
    }

}