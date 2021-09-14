package io.jawg

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.gson.JsonParser
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils
import kotlinx.android.synthetic.main.activity_popup_map.*

class PopupMapActivity : AppCompatActivity() {
    companion object {
        private const val MARKER_SELECTED_ICON = "JAWG_ICON"
        private const val MARKER_ICON = "MARKER_ICON"
    }

    private var mapView: MapView? = null
    private var symbolManager: SymbolManager? = null
    private var lastSymbol: Symbol? = null

    // Returns the Jawg url depending on the style given (jawg-streets by default)
    // See /res/values/strings which contains the url, the list of styles and your access token.
    private fun makeStyleUrl(style: String = "jawg-streets"): String {
        return "${getString(R.string.jawg_styles_url) + style}.json?access-token=${getString(R.string.jawg_access_token)}";
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the Mapbox context.
        Mapbox.getInstance(this)
        // Then set the activity layout.
        setContentView(R.layout.activity_popup_map)

        // We get the map view to set its style with the desired Jawg url.
        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->
            map.setStyle(makeStyleUrl()) { style ->
                // Map fully loaded in this scope.
                // Update attributions position
                map.uiSettings.setAttributionMargins(15, 0, 0, 15)
                // Add an icons ("classic" and "selected") to the map style.
                val selectedMarkerIconDrawable =
                    ResourcesCompat.getDrawable(this.resources, R.drawable.ic_marker_green, null)
                style.addImage(MARKER_ICON, BitmapUtils.getBitmapFromDrawable(selectedMarkerIconDrawable)!!)
                val markerIconDrawable = ResourcesCompat.getDrawable(this.resources, R.drawable.ic_jawg_marker, null)
                style.addImage(MARKER_SELECTED_ICON, BitmapUtils.getBitmapFromDrawable(markerIconDrawable)!!)

                // Initialize SymbolManager.
                this.symbolManager = SymbolManager(mapView!!, map, style)
                this.symbolManager?.iconAllowOverlap = true
                this.symbolManager?.iconIgnorePlacement = true

                // Insert markers with their associated data.
                insertIconOnMap(
                    LatLng(51.50853, -0.076132),
                    "Tower of London",
                    R.drawable.img_tower_london,
                    "It is a historic castle on the north bank of the River Thames in central London." +
                            "It lies within the London Borough of Tower Hamlets, which is separated from the eastern edge of the square mile of the City of London by the open space known as Tower Hill." +
                            "It was founded towards the end of 1066 as part of the Norman Conquest of England."
                )
                insertIconOnMap(
                    LatLng(51.501476, -0.140634),
                    "Buckingham Palace",
                    R.drawable.img_buckingham_palace,
                    "Located in the City of Westminster, the palace is often at the centre of state occasions and royal hospitality." +
                            "The building at the core of today's palace was a large townhouse built for the Duke of Buckingham in 1703." +
                            "It was acquired by King George III in 1761 as a private residence for Queen Charlotte and became known as The Queen's House."
                )
                insertIconOnMap(
                    LatLng(51.503399, -0.119519),
                    "London Eye",
                    R.drawable.img_london_eye,
                    "The London Eye, or the Millennium Wheel, is a cantilevered observation wheel on the South Bank of the River Thames in London." +
                            "It is Europe's tallest cantilevered observation wheel, and is the most popular paid tourist attraction in the United Kingdom with over 3 million visitors annually, and has made many appearances in popular culture."
                )

                // Add a listener to trigger markers clicks.
                this.symbolManager?.addClickListener {
                    // Put all marker information into the layout.
                    titleView.text = it.data?.asJsonObject?.get("title")?.asString
                    descriptionView.text = it.data?.asJsonObject?.get("description")?.asString
                    descriptionLayout.background = getDrawable(it.data?.asJsonObject?.get("imageId")?.asInt!!)
                    descriptionLayout.background.alpha = 30
                    // Set the new marker as selected and toggle layout.
                    setSelectedIcon(it)
                    toggleLayout()
                    true
                }
            }

        }
    }

    // Toggle the bottom layout to display information.
    private fun toggleLayout() {
        mapLayout?.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2f)
        descriptionLayout?.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f)
    }

    private fun setDefaultIcon(symbol: Symbol) {
        symbol.iconImage = MARKER_ICON
        symbol.iconSize = 0.24f
        symbolManager?.update(symbol)
    }

    private fun setSelectedIcon(symbol: Symbol) {
        symbol.iconImage = MARKER_SELECTED_ICON
        symbol.iconSize = 0.25f
        symbolManager?.update(symbol)

        if (this.lastSymbol != null) {
            setDefaultIcon(this.lastSymbol!!)
        }
        this.lastSymbol = symbol
    }

    private fun insertIconOnMap(point: LatLng, title: String, imageId: Int, description: String) {
        // Convert datas of the marker into Json object.
        val jsonData = """
            {
                "title" : "$title",
                "imageId" : "$imageId",
                "description" : "$description"
            }
        """
        // Add symbol at specified lat/lon.
        val newSymbol = symbolManager!!.create(
            SymbolOptions()
                .withLatLng(LatLng(point.latitude, point.longitude))
                .withData(JsonParser.parseString(jsonData))
        )
        setDefaultIcon(newSymbol)
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