package io.jawg.example.maplibre

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (getString(R.string.jawg_access_token).isBlank()) {
            Toast.makeText(
                applicationContext,
                "Please enter your access token in strings.xml",
                Toast.LENGTH_LONG
            ).show()
        }

        val buttonToClassicMap: Button = findViewById(R.id.buttonToSimpleMap)
        buttonToClassicMap.setOnClickListener {
            val intent = Intent(this, SimpleMapActivity::class.java)
            startActivity(intent)
        }

        val buttonToStylesMap: Button = findViewById(R.id.buttonToStylesMap)
        buttonToStylesMap.setOnClickListener {
            val intent = Intent(this, DifferentStylesActivity::class.java)
            startActivity(intent)
        }

        val buttonToGeometryMap: Button = findViewById(R.id.buttonToGeometryMap)
        buttonToGeometryMap.setOnClickListener {
            val intent = Intent(this, GeometryMapActivity::class.java)
            startActivity(intent)
        }

        val buttonToPopupMap: Button = findViewById(R.id.buttonToPopupMap)
        buttonToPopupMap.setOnClickListener {
            val intent = Intent(this, PopupMapActivity::class.java)
            startActivity(intent)
        }

        val buttonToLanguageMap: Button = findViewById(R.id.buttonToLanguageMap)
        buttonToLanguageMap.setOnClickListener {
            val intent = Intent(this, SwitchLanguagesActivity::class.java)
            startActivity(intent)
        }

        val buttonToCustomMap: Button = findViewById(R.id.buttonToCustomMap)
        buttonToCustomMap.setOnClickListener {
            val intent = Intent(this, CustomStyleActivity::class.java)
            startActivity(intent)
        }

        val buttonToAddAMarker: Button = findViewById(R.id.buttonToAddAMarker)
        buttonToAddAMarker.setOnClickListener {
            val intent = Intent(this, MarkerMapActivity::class.java)
            startActivity(intent)
        }

        val buttonToDataMap: Button = findViewById(R.id.buttonToDataMap)
        buttonToDataMap.setOnClickListener {
            val intent = Intent(this, DataMapActivity::class.java)
            startActivity(intent)
        }
    }
}