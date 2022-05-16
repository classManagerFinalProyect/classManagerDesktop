package data.api

import data.remote.AppUser
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceUser {

    @GET("users")
    suspend fun getUsers() : Response<List<AppUser>>

    @GET("user/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<AppUser>

    @POST("user")
    suspend fun postUser(
        @Body newUser: AppUser
    ): Response<AppUser>

    @PUT("user")
    suspend fun putUser(
        @Body newUser: AppUser
    ): Response<AppUser>

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