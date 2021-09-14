package io.jawg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView


class SimpleMapActivity : AppCompatActivity() {
    private var mapView: MapView? = null

    // Returns the Jawg url depending on the style given (jawg-streets by default)
    // See /res/values/strings which contains the url, the list of styles and your access token.
    private fun makeStyleUrl(style: String = "jawg-streets"): String {
        return "${getString(R.string.jawg_styles_url) + style}.json?access-token=${getString(R.string.jawg_access_token)}";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the Mapbox context.
        Mapbox.getInstance(this)
        // Then set the activity layout
        setContentView(R.layout.activity_simple_map)

        // We get the map view to set its style with the desired Jawg url.
        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->
            map.setStyle(makeStyleUrl()) {
                // Map fully loaded in this scope.
                // Update attributions position
                map.uiSettings.setAttributionMargins(15, 0, 0, 15)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

}