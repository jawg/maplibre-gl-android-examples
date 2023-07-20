package io.jawg.example.maplibre

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView

class SwitchLanguagesActivity : Activity(), AdapterView.OnItemSelectedListener {

    companion object {
        const val DEFAULT_LANGUAGE = "en"
    }

    private lateinit var mapView: MapView

    /**
     * Get the Jawg Style URL for a given [lang].
     */
    private fun getStyleUrl(lang: String): String {
        val accessToken = getString(R.string.jawg_access_token)
        return "https://api.jawg.io/styles/jawg-streets.json?lang=${lang}&access-token=${accessToken}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the Mapbox context.
        Mapbox.getInstance(this)
        // Then set the activity layout
        setContentView(R.layout.activity_switch_languages)

        // Here we fill the spinner with the different languages handled by Jawg.
        val spinner: Spinner = findViewById(R.id.language_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.jawg_languages,
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
            map.setStyle(getStyleUrl(DEFAULT_LANGUAGE))
        }
    }

    // Behavior on item selection in the spinner.
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        mapView.getMapAsync { map ->
            // We update the map style using the value selected from the spinner.
            val lang = parent.getItemAtPosition(pos).toString()
            map.setStyle(getStyleUrl(lang))
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