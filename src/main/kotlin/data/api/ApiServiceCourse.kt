package data.api

import data.remote.Course
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceCourse {


    @GET("course/{id}")
    suspend fun getCourserById(
        @Path("id") id: String
    ): Response<Course>


    @GET("courses/{listOfIds}")
    suspend fun getCourserByListOfIds(
        @Path("listOfIds") listOfIds: MutableList<String>
    ): Response<MutableList<Course>>

    @POST("course")
    suspend fun postCourse(
        @Body newCourse: Course
    ): Response<Course>

    @PUT("course")
    suspend fun putCourse(
        @Body updateCourse: Course
    ): Response<Course>

    @DELETE("course/{id}")
    suspend fun deleteCourseById(
        @Path(value = "id") id: String
    ): Response<Unit>

    /*
    @POST("addNewMember/{idOfCourse}")
    suspend fun addNewMember(
        @Path("idOfCourse") idOfCourse: String,
        @Body rolUser: RolUser,
    ): Response<Course>

    @POST("addNewMember/{idOfUser}/{rol}/{idOfCourse}")
    suspend fun addNewMember(
        @Path("rol") rol: String,
        @Body("idOfUser") idOfUser: String,
        @Body("idOfCourse") idOfCourse: String,
    ): Response<Course>
*/


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