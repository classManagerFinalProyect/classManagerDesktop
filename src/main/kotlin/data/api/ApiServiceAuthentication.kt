package data.api

import data.local.NewUser
import data.remote.AppUser
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiServiceAuthentication {

    @POST("register")
    suspend fun register(
        @Body newUser: NewUser
    ): Response<NewUser>


    @POST("login")
    suspend fun login(
        @Body newUser: NewUser
    ):Response<AppUser>

    @POST("deleteAccount/{uid}")
    suspend fun deleteAccount(
        @Path("uid") uid : String
    ): Response<Unit>

    @POST("updatePasswordByEmail/{email}")
    suspend fun updatePasswordByEmail(
        @Path("email") email : String
    ): Response<Unit>


    companion object {
        private var apiService: ApiServiceAuthentication? = null

        fun getInstance() : ApiServiceAuthentication {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://192.168.18.2:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceAuthentication::class.java)
            }
            return apiService!!
        }
    }
}