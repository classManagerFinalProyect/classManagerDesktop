package data.api

import data.remote.Course
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceCourse {


    @GET("course/{id}")
    suspend fun getCourserById(
        @Path("id") id: String
    ): Response<Course>


    @GET("courses/{listOfIds}")
    suspend fun getCourserByListOfIds(
        @Path("listOfIds") listOfIds: MutableList<String>
    ): Response<MutableList<Course>>

    companion object {
        private var apiService: ApiServiceCourse? = null

        fun getInstance() : ApiServiceCourse {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://192.168.18.2:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceCourse::class.java)
            }
            return apiService!!
        }
    }
}