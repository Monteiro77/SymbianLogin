package br.senai.sp.jandira.service

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpService {

    @Headers("Content-Type: application/json")
    @POST("usuario/cadastrarUsuario")
    suspend fun signupClient(@Body body: JsonObject): Response<JsonObject>
}