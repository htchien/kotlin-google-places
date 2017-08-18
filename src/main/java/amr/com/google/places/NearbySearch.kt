package amr.com.google.places

import com.google.gson.*
import okhttp3.ResponseBody
import retrofit2.*


/**
 * Nearby Search Class - Responsible for preparing and executing the nearby search
 * @param apiKey - String Google API Key
 * @param coordinates - Coordinates Object for the origin of the search
 * @param radius - Integer representing the radius of the search (in meters)
 * @param mListener- NearbySearch Listener (Required if Async Search is used using enqueueSearch() function)
 *
 */
class NearbySearch(val apiKey: String, val coordinates: Coordinates, val radius: Int, val mListener: NearbySearchListener? = null):Callback<ResponseBody>{

    init {
        if(radius > 50000){
            throw IllegalStateException("Radius Can't Exceed 50000")
        }
    }

    //Retrofit Response Callbacks
    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
        if (t != null) {
            this.mListener?.onError(t)
        }
    }
    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
        if(response != null ) {
            if (response.isSuccessful && response.body() != null) {
                val jsonResponse = JsonParser().parse(response.body()!!.string()).asJsonObject;
                if(jsonResponse.has("error_message")){
                    this.mListener?.onError(Throwable(jsonResponse.get("error_message").asString))
                }else {
                    this.mListener?.onResultsReady(createPlaceList(jsonResponse.toString()))
                }
            } else{
                this.mListener?.onError(Throwable(response.errorBody().toString()))
            }
        }else{
            this.mListener?.onError(Throwable("Response was null..Something bad happened"))
        }
    }

    val parametersMap:HashMap<String,String>
    init {
        parametersMap = generateParamsHashMap()
    }

    /**
     * Asynchronous Execution of the search operation
     */
    fun enqueueSearch(){
        if(mListener == null){
            throw NullPointerException("Listener can't be null.")
        }else {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/maps/api/place/")
                    .build()

            val googleMapsAPIService = retrofit.create(GoogleMapsAPIService::class.java)
            val call = googleMapsAPIService.getNearbyPlaces(parametersMap)
            call.enqueue(this)
        }
    }

    /**
     * Synchronous Execution of the search operation
     */
    fun executeSearch():List<Place>{
        val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/")
                .build()

        val googleMapsAPIService = retrofit.create(GoogleMapsAPIService::class.java)
        val call = googleMapsAPIService.getNearbyPlaces(parametersMap)
        return createPlaceList(call.execute().body()!!.string())
    }


    /**
     * Generates parameters Hashmap for retrofit
     */
    private fun generateParamsHashMap(): HashMap<String, String> {
        val map:HashMap<String,String> = HashMap()
        map.put("key",apiKey)
        map.put("location","${coordinates.latitude},${coordinates.longitude}")
        map.put("radius", radius.toString())
        return  map
    }


    /**
     * Parses JSON response from the server and returns list of places (Search Results)
     * @param result - String representation of JSON results
     * @return List<Place> - list of places returned from the server
     */
    private fun createPlaceList(result:String):List<Place>{

        val json = JsonParser().parse(result)

        val places:ArrayList<Place> = ArrayList()

        val resultObject = json?.asJsonObject
        if(resultObject!!.has("results")) {
            for(element: JsonElement in resultObject!!.getAsJsonArray("results")) {
                val jsonObject = element.asJsonObject
                val place: Place = Place(
                        name = if (jsonObject!!.has("name")) jsonObject.get("name")!!.asString else "",
                        location = Coordinates(
                                latitude = if (jsonObject.has("location")) jsonObject.get("location")!!.asJsonObject.get("lat").asDouble else 0.0,
                                longitude = if (jsonObject.has("location")) jsonObject.get("location")!!.asJsonObject.get("lng").asDouble else 0.0
                        ),
                        iconLink = if (jsonObject.has("icon")) jsonObject.get("icon")!!.asString else "",
                        id = if (jsonObject.has("id")) jsonObject.get("id")!!.asString else "",
                        placeId = if (jsonObject.has("place_id")) jsonObject.get("place_id")!!.asString else "",
                        rating = if (jsonObject.has("rating")) jsonObject.get("rating")!!.asInt else 0,
                        vicinity = if (jsonObject.has("vicinity")) jsonObject.get("vicinity")!!.asString else "",
                        type = getTypes(if (jsonObject.has("types")) jsonObject.get("types")!!.asJsonArray else JsonArray())
                )
                places.add(place)
            }
        }
        return places
    }

    private fun getTypes(jsonArray: JsonArray): List<String> {
        var result:ArrayList<String> = ArrayList()
        for (element:JsonElement in jsonArray){
            result.add(element = element.asString)
        }
        return result
    }
}


