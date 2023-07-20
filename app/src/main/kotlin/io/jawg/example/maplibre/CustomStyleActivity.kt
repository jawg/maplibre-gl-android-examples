package io.jawg.example.maplibre

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView

class CustomStyleActivity : Activity() {

    companion object {
        const val DEFAULT_STYLE = "jawg-streets"
    }

    private lateinit var mapView: MapView

    /*
     * Enter you style ID from the lab here.
     * Our default styles are located here: https://www.jawg.io/docs/apidocs/maps/dynamic-maps/
     * If you want your own style go to the lab: https://jawg.io/lab/styles.
     */
    private val styleId: String = DEFAULT_STYLE

    /**
     * Get the Jawg Style URL for a given [styleId].
     */
    private fun getStyleUrl(styleId: String): String {
        val accessToken = getString(R.string.jawg_access_token)
        return "https://api.jawg.io/styles/$styleId.json?access-token=$accessToken"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get and create the Mapbox context.
        Mapbox.getInstance(this)

        // Then set the activity layout
        setContentView(R.layout.activity_custom_styles)

        val input: EditText = findViewById(R.id.edit_text)
        input.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val styleId = v.text.toString()
                if (styleId.isBlank()) {
                    Toast.makeText(applicationContext, "Enter a style id", Toast.LENGTH_LONG).show()
                    return@setOnEditorActionListener false
                }
                updateStyle(v.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        updateStyle(styleId)
    }

    private fun updateStyle(styleId: String) {
        mapView.getMapAsync { map ->
            map.setStyle(getStyleUrl(styleId))
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