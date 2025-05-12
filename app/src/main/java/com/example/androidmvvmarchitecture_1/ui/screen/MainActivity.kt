package com.example.androidmvvmarchitecture_1.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidmvvmrchitecture_1.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}