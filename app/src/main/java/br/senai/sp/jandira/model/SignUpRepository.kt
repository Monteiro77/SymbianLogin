package br.senai.sp.jandira.model

import br.senai.sp.jandira.service.RetrofitFactory
import com.google.gson.JsonObject
import retrofit2.Response

class SignUpRepository{
    private val service = RetrofitFactory
        .getSignUp()

    suspend fun signupClient(email: String, senha: String, foto: String): Response<JsonObject> {

        val requestBody = JsonObject().apply {

            addProperty("login", email)
            addProperty("senha", senha)
            addProperty("imagem", foto)

        }

        return service.signupClient(requestBody)

    }
}
