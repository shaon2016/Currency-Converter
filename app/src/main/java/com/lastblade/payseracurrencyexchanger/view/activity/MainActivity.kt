package com.lastblade.payseracurrencyexchanger.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lastblade.payseracurrencyexchanger.R
import com.lastblade.payseracurrencyexchanger.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}