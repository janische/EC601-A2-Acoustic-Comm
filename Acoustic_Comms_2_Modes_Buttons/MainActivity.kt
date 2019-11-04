package com.example.lowcostacousticcomm

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val REQUEST_RECORD_AUDIO = 1
const val CHIRP_APP_KEY = "";
const val CHIRP_APP_SECRET = "";
const val CHIRP_APP_CONFIG = "";


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val aboveWatButton = findViewById<Button>(R.id.button1)
        // set on-click listener
        aboveWatButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivity, "Above-Water Mode", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AboveWaterMode::class.java)
            startActivity(intent)
        }
        val underWatButton = findViewById<Button>(R.id.button2)
        // set on-click listener
        underWatButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivity, "Underwater Mode", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UnderwaterMode::class.java)
            startActivity(intent)
        }
    }

    fun mode(view: View) {
        val id = view.id
        val stringId = resources.getResourceName(id).substringAfter("/")

        if (stringId == "button1") {
            val textView = findViewById<TextView>(R.id.textView).apply {
                text = stringId
            }
            var intent = Intent(this, AboveWaterMode::class.java)
            startActivity(intent)
        } else {
            val textView = findViewById<TextView>(R.id.textView).apply {
                text = stringId
            }
            var intent = Intent(this, UnderwaterMode::class.java)
            startActivity(intent)
        }

    }
}
