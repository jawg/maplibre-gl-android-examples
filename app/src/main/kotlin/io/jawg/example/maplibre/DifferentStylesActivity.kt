package io.jawg.example.maplibre

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView

class DifferentStylesActivity : ComponentActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        const val DEFAULT_STYLE = "jawg-streets"
    }

    private lateinit var mapView: MapView

    /**
     * Get the Jawg Style URL for a given [styleId].
     */
    private fun getStyleUrl(styleId: String): String {
        val accessToken = getString(R.string.jawg_access_token)
        return "https://api.jawg.io/styles/$styleId.json?access-token=$accessToken"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the Mapbox context.
        Mapbox.getInstance(this)
        // Then set the activity layout
        setContentView(R.layout.activity_different_styles)

        // Here we fill the spinner with the different styles of Jawg.
        val spinner: Spinner = findViewById(R.id.style_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.jawg_styles,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        // The spinner will follow the onItemSelected behavior overrode here.
        spinner.onItemSelectedListener = this

        // We get the map view to set its style with the desired Jawg url.
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            map.setStyle(getStyleUrl(DEFAULT_STYLE))
        }
    }

    // Behavior on item selection in the spinner.
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        mapView.getMapAsync { map ->
            // We update the map style using the value selected from the spinner.
            map.setStyle(getStyleUrl(parent.getItemAtPosition(pos).toString()))
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Do nothing.
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