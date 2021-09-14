package io.jawg

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import kotlinx.android.synthetic.main.activity_different_styles.*

class DifferentStylesActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var mapView: MapView? = null

    // Returns the Jawg url depending on the style given.
    // See /res/values/strings which contains the url, the list of styles and your access token.
    private fun makeStyleUrl(style: String = "jawg-streets"): String {
        return "${getString(R.string.jawg_styles_url) + style}.json?access-token=${getString(R.string.jawg_access_token)}";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the Mapbox context.
        Mapbox.getInstance(this)
        // Then set the activity layout
        setContentView(R.layout.activity_different_styles)

        // Here we fill the spinner with the different styles of Jawg.
        val spinner: Spinner = style_spinner
        ArrayAdapter.createFromResource(this, R.array.jawg_styles, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        // The spinner will follow the onItemSelected behavior overrode here.
        spinner.onItemSelectedListener = this

        // We get the map view to set its style with the desired Jawg url.
        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->
            map.setStyle(makeStyleUrl()) {
                // Map fully loaded in this scope.
            }
        }
    }

    // Behavior on item selection in the spinner.
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        mapView?.getMapAsync { map ->
            // We udate the map style using the value selected from the spinner.
            map.setStyle(makeStyleUrl(parent.getItemAtPosition(pos).toString())) {
                // Map fully loaded in this scope.
                // Update attributions position
                map.uiSettings.setAttributionMargins(15, 0, 0, 15)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Do nothing.
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