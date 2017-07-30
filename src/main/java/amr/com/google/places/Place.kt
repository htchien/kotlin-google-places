package amr.com.google.places

/**
 * Data Model to describe the location returned from Google Places API
 *
 * @param location - Coordinates Object to hold latitude and longitude
 * @param iconLink - URL of the place icon
 * @param placeId - String ID of the Place
 * @param name - Name of the Place
 * @param rating - Rating of place (Stars Count)
 * @param vicinity - contains a feature name of a nearby location.
 *
 */
data class Place(val location:Coordinates,
                 val iconLink:String,
                 val id:String,
                 val name:String,
                 val placeId:String,
                 val rating:Int,
                 val vicinity:String,
                 val type:List<String>)