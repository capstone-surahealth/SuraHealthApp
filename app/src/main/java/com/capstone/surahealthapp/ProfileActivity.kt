package com.capstone.surahealthapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.capstone.surahealthapp.databinding.ActivityProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNav = binding.bottomBar2
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.b_home -> {
                    loadActivity(MainActivity::class.java)
                    true
                }
                else -> {
                    true
                }
            }
        }

    }

    private fun loadActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        finish()
    }
}