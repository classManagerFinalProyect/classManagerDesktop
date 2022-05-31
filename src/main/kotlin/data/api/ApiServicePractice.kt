package data.api

import data.remote.Practice
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServicePractice {

    @GET("practice/{id}")
    suspend fun getPracticeById(
        @Path("id") id: String
    ): Response<Practice>

    @POST("practice")
    suspend fun postPractice(
        @Body newPractice: Practice
    ): Response<Practice>

    @DELETE("practice/{id}")
    suspend fun deletePracticeById(
        @Path(value = "id") id: String
    ): Response<Unit>

    companion object {
        private var apiService: ApiServicePractice? = null

        fun getInstance() : ApiServicePractice {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://192.168.18.2:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServicePractice::class.java)
            }
            return apiService!!
        }
    }
}