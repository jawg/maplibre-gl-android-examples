package io.jawg.example.maplibre

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.google.gson.JsonParser
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils

class PopupMapActivity : Activity() {

    companion object {
        private const val SELECTED_MARKER_NAME = "selected-marker"
        private const val DEFAULT_MARKER_NAME = "default-marker"
    }

    private lateinit var mapView: MapView
    private lateinit var symbolManager: SymbolManager
    private var lastSymbol: Symbol? = null
    private val descriptionLayout: LinearLayout by lazy { findViewById(R.id.descriptionLayout) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get your access-token on the Lab: https://www.jawg.io/lab/access-tokens
        val accessToken = getString(R.string.jawg_access_token)
        val styleId = "jawg-streets"
        val styleUrl = "https://api.jawg.io/styles/$styleId.json?access-token=$accessToken"

        // Get the Mapbox context.
        Mapbox.getInstance(this)
        // Then set the activity layout.
        setContentView(R.layout.activity_popup_map)

        // We get the map view to set its style with the desired Jawg url.
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            map.setStyle(styleUrl) { style ->
                // Add 2 icons (the "default" and the "selected") to the map style.
                val defaultMarkerDrawable =
                    ResourcesCompat.getDrawable(this.resources, R.drawable.ic_marker_default, null)
                style.addImage(
                    DEFAULT_MARKER_NAME,
                    BitmapUtils.getBitmapFromDrawable(defaultMarkerDrawable)!!
                )
                val selectedMarkerDrawable =
                    ResourcesCompat.getDrawable(this.resources, R.drawable.ic_marker_selected, null)
                style.addImage(
                    SELECTED_MARKER_NAME,
                    BitmapUtils.getBitmapFromDrawable(selectedMarkerDrawable)!!
                )

                // Initialize SymbolManager.
                this.symbolManager = SymbolManager(mapView, map, style)
                // Disable symbol collisions
                this.symbolManager.iconAllowOverlap = true
                this.symbolManager.iconIgnorePlacement = true

                // Add markers with their associated data.
                addMarker(
                    point = LatLng(51.50853, -0.076132),
                    title = "Tower of London",
                    description = "It is a historic castle on the north bank of the River Thames in central London. " +
                            "It lies within the London Borough of Tower Hamlets, which is separated from the eastern edge of the square mile of the City of London by the open space known as Tower Hill. " +
                            "It was founded towards the end of 1066 as part of the Norman Conquest of England."
                )
                addMarker(
                    point = LatLng(51.501476, -0.140634),
                    title = "Buckingham Palace",
                    description = "Located in the City of Westminster, the palace is often at the centre of state occasions and royal hospitality. " +
                            "The building at the core of today's palace was a large townhouse built for the Duke of Buckingham in 1703. " +
                            "It was acquired by King George III in 1761 as a private residence for Queen Charlotte and became known as The Queen's House. "
                )
                addMarker(
                    point = LatLng(51.503399, -0.119519),
                    title = "London Eye",
                    description = "The London Eye, or the Millennium Wheel, is a cantilevered observation wheel on the South Bank of the River Thames in London. " +
                            "It is Europe's tallest cantilevered observation wheel, and is the most popular paid tourist attraction in the United Kingdom with over 3 million visitors annually, and has made many appearances in popular culture."
                )

                val titleView: TextView = findViewById(R.id.titleView)
                val descriptionView: TextView = findViewById(R.id.descriptionView)

                // Add a click listener on symbols.
                this.symbolManager.addClickListener { symbol ->
                    // Put all marker information into the layout.
                    val data = symbol.data?.asJsonObject
                    titleView.text = data?.get("title")?.asString
                    descriptionView.text = data?.get("description")?.asString
                    descriptionLayout.visibility = View.VISIBLE
                    // Set the marker as selected and show the description
                    if (lastSymbol != null && symbol != lastSymbol) {
                        setDefaultMarker(lastSymbol!!)
                    }
                    setSelectedMarker(symbol)
                    this.lastSymbol = symbol
                    true
                }
            }

        }
    }

    private fun setDefaultMarker(symbol: Symbol) {
        symbol.iconImage = DEFAULT_MARKER_NAME
        symbol.iconSize = 1.0f
        symbolManager.update(symbol)
    }

    private fun setSelectedMarker(symbol: Symbol) {
        symbol.iconImage = SELECTED_MARKER_NAME
        // Slightly increase the icon size
        symbol.iconSize = 1.25f
        symbolManager.update(symbol)
    }

    private fun addMarker(point: LatLng, title: String, description: String) {
        // Convert data of the marker into a JSON object.
        val jsonData = """
            {
                "title": "$title",
                "description": "$description"
            }
        """
        // Add symbol at specified lat/lon.
        val symbol = symbolManager.create(
            SymbolOptions()
                .withLatLng(point)
                .withIconAnchor("bottom")
                .withData(JsonParser.parseString(jsonData))
        )
        setDefaultMarker(symbol)
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