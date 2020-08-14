package io.jawg

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (getString(R.string.jawg_access_token) == "") {
            Toast.makeText(applicationContext, "Please enter your access token in strings.xml", Toast.LENGTH_LONG)
                .show();
        }
        buttonToClassicMap.setOnClickListener {
            val intent = Intent(this, SimpleMapActivity::class.java)
            startActivity(intent)
        }

        buttonToStylesMap.setOnClickListener {
            val intent = Intent(this, DifferentStylesActivity::class.java)
            startActivity(intent)
        }

        buttonToGeometryMap.setOnClickListener {
            val intent = Intent(this, GeometryMapActivity::class.java)
            startActivity(intent)
        }

        buttonToPopupMap.setOnClickListener {
            val intent = Intent(this, PopupMapActivity::class.java)
            startActivity(intent)
        }

        buttonToLanguageMap.setOnClickListener {
            val intent = Intent(this, SwitchLanguagesActivity::class.java)
            startActivity(intent)
        }

        buttonToCustomMap.setOnClickListener {
            val intent = Intent(this, CustomStyleActivity::class.java)
            startActivity(intent)
        }

        buttonToAddAMarker.setOnClickListener {
            val intent = Intent(this, MarkerMapActivity::class.java)
            startActivity(intent)
        }
    }
}