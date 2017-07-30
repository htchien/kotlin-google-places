# Kotlin-Google-Places
This library providers a wrapper for google places web API using Kotlin. It helps you do places search using Google Places API.

At the moment the library supports:


1. Near-by Search

### How to use

In Kotlin

1. Synchronous

```
    val placeWrapper:PlacesWrapper = PlacesWrapper(apiKey = <api-key>)

    //Synchronous Execution of the search operation
        val list = placeWrapper.buildNearbySearch(latitude = <latitude>, longitude = <longitude>, listener =null
        ).executeSearch()
```
2. Asynchronous

```
class SearchPlaces:NearbySearchListener{
    override fun onResultsReady(places: List<Place>) {
        TODO("Handle Results")
    }

    override fun onError(throwable: Throwable) {
        TODO("Handle Errors")
    }

    val placeWrapper:PlacesWrapper = PlacesWrapper(apiKey = "AIzaSyCrMUpjPZLtpUsTkhBTMZLDKpKmsEAhaag")
    
    fun doSearch(){
        placeWrapper.buildNearbySearch(latitude = 54.596237, longitude = -5.882503, listener = this).enqueueSearch()
    }


}

```