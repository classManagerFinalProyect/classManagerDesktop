package data.api

import data.remote.Chat
import data.remote.Class
import data.remote.Event
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceChat {

    @POST("practiceChat")
    suspend fun postChat(
        @Body newChat: Chat
    ): Response<Chat>

    @PUT("practiceChat")
    suspend fun putChat(
        @Body updateChat: Chat
    ): Response<Chat>

    @DELETE("practiceChat/{id}")
    suspend fun deleteChatById(
        @Path(value = "id") id: String
    ): Response<Unit>

    @GET("practiceChat/{id}")
    suspend fun getChatById(
        @Path("id") id: String
    ): Response<Chat>


    companion object {
        private var apiService: ApiServiceChat? = null

        fun getInstance() : ApiServiceChat {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://192.168.18.2:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceChat::class.java)
            }
            return apiService!!
        }
    }
}