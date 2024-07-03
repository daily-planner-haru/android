package com.haru.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.haru.android.Dailiy.DTO.Event
import com.haru.android.Dailiy.DTO.Reminder
import com.haru.android.databinding.ActivityMainBinding
import com.haru.android.mockup.MockEventQueue

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}