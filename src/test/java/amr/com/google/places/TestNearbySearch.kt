package amr.com.google.places

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.mockingDetails
import com.nhaarman.mockito_kotlin.whenever
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import retrofit2.Call

/**
 * Created by amr on 30/07/17.
 */
@RunWith(JUnit4::class)
class TestNearbySearch{

    val placeWrapper:PlacesWrapper = PlacesWrapper(apiKey = "")

    @Test
    fun testSync(){

        val list = placeWrapper.buildNearbySearch(latitude = 54.596237, longitude = -5.882503, listener =null
        ).executeSearch()
        Assert.assertTrue(list.isNotEmpty())
        print(list.size)
    }

    @Test
    fun testAsyncWithNullListener(){

        try {
            placeWrapper.buildNearbySearch(latitude = 54.596237, longitude = -5.882503, listener = null).enqueueSearch()
        }catch (e:NullPointerException){
            Assert.assertTrue(e.message!!.equals("Listener can't be null.",true))
        }
    }
    @Test
    fun testAsync(){
        Assert.assertTrue(true)
    }
}