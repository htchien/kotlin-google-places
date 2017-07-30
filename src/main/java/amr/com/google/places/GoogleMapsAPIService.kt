package amr.com.google.places

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Interface for Google API endpoints
 */
interface GoogleMapsAPIService{
    @POST("nearbysearch/json")

    fun getNearbyPlaces(@QueryMap params:Map<String,String> ): Call<ResponseBody>

}