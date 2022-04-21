package data.api

import data.remote.Event
import data.remote.appUser
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceEvent {

    @GET("event/{id}")
    suspend fun getEventById(
        @Path("id") id: String
    ): Response<Event>


    companion object {
        private var apiService: ApiServiceEvent? = null

        fun getInstance() : ApiServiceEvent {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://192.168.18.2:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceEvent::class.java)
            }
            return apiService!!
        }
    }
}