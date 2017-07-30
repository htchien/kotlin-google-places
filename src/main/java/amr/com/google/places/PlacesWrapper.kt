package amr.com.google.places




/**
 *
 * Wrapper for Google Place Web API
 * @param apiKey - String API Key (https://developers.google.com/places/web-service/get-api-key)
 *
 */
class PlacesWrapper(val apiKey:String){

    /**
     * Builds a NearbySearch object and returns it. NearbySearch Object can be used to execute a search task
     * @param latitude - Latitude of search origin
     * @param longitude - Longitude of search origin
     * @param radius - Optional parameter to specify search radius (Default value = 3000)
     * @param listener - Listener to get the results when Network Call is executed
     */
    fun buildNearbySearch(latitude:Double,longitude:Double, radius:Int = 3000,listener: NearbySearchListener? = null):NearbySearch{

        return NearbySearch(apiKey,Coordinates(latitude,longitude),radius, listener)

    }

}