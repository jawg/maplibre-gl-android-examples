package io.jawg.example.maplibre

import android.app.Activity
import android.os.Bundle
import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource

class GeometryMapActivity : Activity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get your access-token on the Lab: https://www.jawg.io/lab/access-tokens
        val accessToken = getString(R.string.jawg_access_token)
        val styleId = "jawg-dark"
        val styleUrl = "https://api.jawg.io/styles/$styleId.json?access-token=$accessToken"

        // Get the Mapbox context.
        Mapbox.getInstance(this)
        // Then set the activity layout
        setContentView(R.layout.activity_geometry_map)

        // We get the map view to set its style with the desired Jawg URL.
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            map.setStyle(styleUrl) { style ->
                // Once the style is loaded we create a GeoJSON polygon of Paris boundaries.
                val geojson =
                    """
                    {
                        "type": "Feature",
                        "properties": {},
                        "geometry": {
                            "type": "Polygon",
                            "coordinates": [
                                [
                                    [2.319887,48.90046],[2.329981,48.901163],
                                    [2.38515,48.902008],[2.394906,48.898444],
                                    [2.397627,48.894578],[2.398846,48.887109],
                                    [2.408308,48.880409],[2.41327,48.872892],
                                    [2.413838,48.864376],[2.416341,48.849234],
                                    [2.412246,48.834539],[2.422139,48.835798],
                                    [2.41939,48.842577],[2.42813,48.841528],
                                    [2.447699,48.844818],[2.463438,48.842089],
                                    [2.467426,48.838891],[2.467582,48.833133],
                                    [2.462696,48.81906],[2.458705,48.81714],
                                    [2.438448,48.818232],[2.421462,48.824054],
                                    [2.406032,48.827615],[2.390939,48.826079],
                                    [2.379296,48.821214],[2.363947,48.816314],
                                    [2.345958,48.816036],[2.331898,48.817011],
                                    [2.332461,48.818247],[2.292196,48.827142],
                                    [2.279052,48.83249],[2.272793,48.82792],
                                    [2.263174,48.83398],[2.255144,48.83481],
                                    [2.251709,48.838822],[2.250612,48.845555],
                                    [2.239978,48.849702],[2.224219,48.853517],
                                    [2.228225,48.865183],[2.231736,48.869069],
                                    [2.245678,48.876435],[2.25541,48.874264],
                                    [2.258467,48.880387],[2.277487,48.877968],
                                    [2.282327,48.883923],[2.291507,48.889472],
                                    [2.319887,48.90046]
                                ]
                            ]
                        }
                    }
                    """

                // Create feature object from the GeoJSON we declared.
                val parisBoundariesFeature = Feature.fromJson(geojson)
                // Create a GeoJson Source from our feature.
                val geojsonSource = GeoJsonSource("paris-boundaries", parisBoundariesFeature)
                // Add the source to the style
                style.addSource(geojsonSource)

                // Create a layer with the desired style for our source.
                val layer = LineLayer("paris-boundaries", "paris-boundaries")
                    .withProperties(
                        PropertyFactory.lineCap(Property.LINE_CAP_SQUARE),
                        PropertyFactory.lineJoin(Property.LINE_JOIN_MITER),
                        PropertyFactory.lineOpacity(0.7f),
                        PropertyFactory.lineWidth(4f),
                        PropertyFactory.lineColor("#0094ff")
                    )
                // Add the layer at the end
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