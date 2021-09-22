package io.jawg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.utils.BitmapUtils
import java.net.URI

class DataMapActivity : AppCompatActivity() {

    companion object {
        private const val MARKER_ICON = "MARKER_ICON"
    }

    private var mapView: MapView? = null

    // Returns the Jawg url depending on the style given (jawg-streets by default)
    // See /res/values/strings which contains the url, the list of styles and your access token.
    private fun makeStyleUrl(style: String = "jawg-streets"): String {
        return "${getString(R.string.jawg_styles_url) + style}.json?access-token=${getString(R.string.jawg_access_token)}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the Mapbox context.
        Mapbox.getInstance(this)
        // Then set the activity layout
        setContentView(R.layout.activity_data_map)

        // We get the map view to set its style with the desired Jawg url.
        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->
            map.setStyle(makeStyleUrl()) {
                // Map fully loaded in this scope.
                // Update attributions position
                map.uiSettings.setAttributionMargins(15, 0, 0, 15)

                // Add an image to use as a custom marker
                val selectedMarkerIconDrawable =
                    ResourcesCompat.getDrawable(this.resources, R.drawable.ic_jawg_marker, null)
                it.addImage(
                    MARKER_ICON,
                    BitmapUtils.getBitmapFromDrawable(selectedMarkerIconDrawable)!!
                )

                // Create a GeoJson Source from our remote geojson.
                val geoJsonUrl = "https://media.jawg.io/add-your-data/marketplaces.geojson"
                val geoJsonSource = GeoJsonSource("geojson-paris-markets", URI(geoJsonUrl))

                // Add it to the map
                it.addSource(geoJsonSource)

                // Create a layer with the desired style for our source.
                val layer = SymbolLayer("paris-market-pois", "geojson-paris-markets")
                    .withProperties(
                        PropertyFactory.iconImage(MARKER_ICON),
                        PropertyFactory.iconSize(0.2f),
                        PropertyFactory.iconAnchor(Property.ICON_ANCHOR_BOTTOM)
                    )

                // Add the layer to the map
                it.addLayer(layer)

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