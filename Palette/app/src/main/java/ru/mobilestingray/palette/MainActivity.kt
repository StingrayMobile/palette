package ru.mobilestingray.palette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mobilestingray.mylibrary.PaletteActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(
            Intent(this, PaletteActivity::class.java)
        )
    }
}