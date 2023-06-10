package com.capstone.surahealthapp.view.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.surahealthapp.R
import com.capstone.surahealthapp.databinding.ActivityMainBinding
import com.capstone.surahealthapp.view.pertolonganpertama.PertolonganPertamaActivity
import com.capstone.surahealthapp.view.rumahsakit.RumahSakitActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    lateinit var bottomNav: BottomNavigationView
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNav = binding.bottomBar
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.b_profil -> {
                    Toast.makeText(this@MainActivity, resources.getString(R.string.no_fitur), Toast.LENGTH_SHORT).show()
                    //loadActivity(ProfileActivity()::class.java)
                    true
                }
                else -> {
                    true
                }
            }
        }

        binding.cvRumahSakit.setOnClickListener{
            startActivity(Intent(this, RumahSakitActivity::class.java))
        }
        binding.cvPertolonganPertama.setOnClickListener{
            startActivity(Intent(this, PertolonganPertamaActivity::class.java))
        }
    }

    private fun loadActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        finish()
    }

}