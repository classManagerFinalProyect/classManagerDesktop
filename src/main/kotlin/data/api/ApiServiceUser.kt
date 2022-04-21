package data.api

import data.remote.appUser
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceUser {

    @GET("users")
    suspend fun getUsers() : Response<List<appUser>>

    @GET("user/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<appUser>

    @POST("user")
    suspend fun postUser(
        @Body newUser: appUser
    ): Response<appUser>

    @PUT("user")
    suspend fun putUser(
        @Body newUser: appUser
    ): Response<appUser>

    companion object {
        private var apiService: ApiServiceUser? = null

        fun getInstance() : ApiServiceUser {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://192.168.18.2:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceUser::class.java)
            }
            return apiService!!
        }
    }

}