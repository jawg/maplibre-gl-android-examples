package io.jawg.example.maplibre

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import org.maplibre.android.MapLibre
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.plugins.annotation.SymbolManager
import org.maplibre.android.plugins.annotation.SymbolOptions
import org.maplibre.android.utils.BitmapUtils

class MarkerMapActivity : Activity() {

    companion object {
        private const val MARKER_NAME = "marker-pin"
    }

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get your access-token on the Lab: https://www.jawg.io/lab/access-tokens
        val accessToken = getString(R.string.jawg_access_token)
        val styleId = "jawg-streets"
        val styleUrl = "https://api.jawg.io/styles/$styleId.json?access-token=$accessToken"

        // Init MapLibre
        MapLibre.getInstance(this)
        // Then set the activity layout
        // We use the same layout as SimpleMapActivity
        setContentView(R.layout.activity_simple_map)

        // We get the map view to set its style with the desired Jawg URL.
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            map.setStyle(styleUrl) { style ->
                // Choose logo to display
                val drawable = ResourcesCompat.getDrawable(
                    this.resources,
                    R.drawable.ic_marker_default,
                    null
                )
                style.addImage(MARKER_NAME, BitmapUtils.getBitmapFromDrawable(drawable)!!)

                // Create a SymbolManager
                val symbolManager = SymbolManager(mapView, map, style)
                // Disable symbol collisions
                symbolManager.iconAllowOverlap = true
                symbolManager.iconIgnorePlacement = true

                // Add a new symbol at specified lat/lon.
                val symbol = symbolManager.create(
                    SymbolOptions()
                        .withLatLng(LatLng(-33.85416325, 151.20916))
                        .withIconImage(MARKER_NAME)
                        .withIconSize(1.25f)
                        .withIconAnchor("bottom")
                )
                symbolManager.update(symbol)

                // Add a listener to trigger markers clicks.
                symbolManager.addClickListener {
                    // Display information
                    Toast.makeText(this, "Opera house", Toast.LENGTH_LONG).show();
                    true
                }
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