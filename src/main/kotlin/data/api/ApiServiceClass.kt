package data.api

import data.remote.Class
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceClass {


    @GET("class/{id}")
    suspend fun getClassById(
        @Path("id") id: String
    ): Response<Class>

    @POST("class")
    suspend fun postClass(
        @Body newClass: Class
    ): Response<Class>

    @PUT("class")
    suspend fun putClass(
        @Body newClass: Class
    ): Response<Class>

    @DELETE("class/{id}")
    suspend fun deleteClassById(
        @Path(value = "id") id: String
    ): Response<Unit>

    companion object {
        private var apiService: ApiServiceClass? = null

        fun getInstance() : ApiServiceClass {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://192.168.18.2:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceClass::class.java)
            }
            return apiService!!
        }
    }
}