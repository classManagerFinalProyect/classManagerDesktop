package data.api

import data.remote.Event
import data.remote.appUser
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceEvent {

    @GET("event/{id}")
    suspend fun getEventById(
        @Path("id") id: String
    ): Response<Event>

    @POST("event")
    suspend fun postEvent(
        @Body uploadEvent: Event
    ): Response<Event>

    @PUT("event")
    suspend fun updateEvent(
        @Body updateEvent: Event
    ): Response<Event>

    @DELETE("event/{id}")
    suspend fun deleteEventById(
        @Path("id") id: String
    ): Response<Unit>



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