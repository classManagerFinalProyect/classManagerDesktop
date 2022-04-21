package data.api

import data.remote.Class
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceClass {


    @GET("class/{id}")
    suspend fun getClassById(
        @Path("id") id: String
    ): Response<Class>

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