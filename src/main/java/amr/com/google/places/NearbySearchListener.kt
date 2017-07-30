package amr.com.google.places

/**
 * Created by amr on 30/07/17.
 */
interface NearbySearchListener{
    fun onResultsReady(places:List<Place>)
    fun onError(throwable: Throwable)
}