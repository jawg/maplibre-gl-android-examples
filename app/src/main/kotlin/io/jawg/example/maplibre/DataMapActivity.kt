package io.jawg.example.maplibre

import android.app.Activity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.PropertyValue
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.utils.BitmapUtils
import java.net.URI

class DataMapActivity : Activity() {

    companion object {
        private const val MARKER_ICON = "MARKER_ICON"
    }

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get your access-token on the Lab: https://www.jawg.io/lab/access-tokens
        val accessToken = getString(R.string.jawg_access_token)
        val styleId = "jawg-streets"
        val styleUrl = "https://api.jawg.io/styles/$styleId.json?access-token=$accessToken"

        // Get the Mapbox context.
        Mapbox.getInstance(this)
        // Then set the activity layout
        setContentView(R.layout.activity_data_map)

        // We get the map view to set its style with the desired Jawg url.
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            map.setStyle(styleUrl) { style ->
                // Map fully loaded in this scope.
                // Add an image to use as a custom marker
                val marker = ResourcesCompat.getDrawable(
                    this.resources,
                    R.drawable.ic_marker_default,
                    null
                )
                style.addImage(
                    MARKER_ICON,
                    BitmapUtils.getBitmapFromDrawable(marker)!!
                )

                // Create a GeoJson Source from our remote GeoJSON.
                val geoJsonUrl = "https://media.jawg.io/add-your-data/marketplaces.geojson"
                val geoJsonSource = GeoJsonSource("geojson-paris-markets", URI(geoJsonUrl))

                // Add it to the map
                style.addSource(geoJsonSource)

                // Create a layer with the desired style for our source.
                val layer = SymbolLayer("paris-market-pois", "geojson-paris-markets")
                    .withProperties(
                        PropertyFactory.iconImage(MARKER_ICON),
                        PropertyFactory.iconAnchor(Property.ICON_ANCHOR_BOTTOM),
                        PropertyFactory.iconAllowOverlap(true)
                    )

                // Add the layer to the map
                style.addLayer(layer)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

}