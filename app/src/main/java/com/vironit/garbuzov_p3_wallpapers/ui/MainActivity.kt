package com.vironit.garbuzov_p3_wallpapers.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vironit.garbuzov_p3_wallpapers.databinding.ActivityMainBinding

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.root
        val view = binding.root
        setContentView(view)
    }
}