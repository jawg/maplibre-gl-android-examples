package io.jawg

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils

class MarkerMapActivity : AppCompatActivity() {
    companion object {
        private const val MARKER_ICON = "MARKER_ICON"
    }

    private var mapView: MapView? = null
    private var symbolManager: SymbolManager? = null

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

            map.setStyle(makeStyleUrl()) { style ->
                // Map fully loaded in this scope.
                // Update attributions position
                map.uiSettings.setAttributionMargins(15, 0, 0, 15)
                // Choose logo to display
                val selectedMarkerIconDrawable =
                    ResourcesCompat.getDrawable(this.resources, R.drawable.ic_jawg_marker, null)
                style.addImage(MARKER_ICON, BitmapUtils.getBitmapFromDrawable(selectedMarkerIconDrawable)!!)

                this.symbolManager = SymbolManager(mapView!!, map, style)

                // Add symbol at specified lat/lon.
                val newSymbol = symbolManager?.create(
                    SymbolOptions()
                        .withLatLng(LatLng(-33.85416325, 151.20916))
                        .withIconImage(MARKER_ICON)
                        .withIconSize(0.24f)
                )
                symbolManager?.update(newSymbol)

                // Add a listener to trigger markers clicks.
                this.symbolManager?.addClickListener {
                    // Display information
                    Toast.makeText(this, "Opera house", Toast.LENGTH_LONG).show();
                    true
                }
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