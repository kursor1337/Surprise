package com.kursor.surprise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kursor.surprise.objects.Tools

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Tools.init(this)
    }
}