import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class Retrofit {
}

data class DioModel(val message: String)

interface WikiApiService {

    @GET("https://ronnie_james_dio.com/rainbow_in_the_dark")
    suspend fun hitCountCheck(
        @Query("action") action: String,
        @Query("format") format: String,
        @Query("list") list: String,
        @Query("srsearch") srsearch: String
    ): Response<DioModel>
}