package com.capstone.surahealthapp.view.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import com.capstone.surahealthapp.R
import com.capstone.surahealthapp.databinding.ActivityMainBinding
import com.capstone.surahealthapp.view.pertolonganpertama.PertolonganPertamaActivity
import com.capstone.surahealthapp.view.rumahsakit.RumahSakitActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    lateinit var bottomNav: BottomNavigationView
    lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Home"
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkGPS()
        getMyLocation()

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

    private fun checkGPS() {
        val locationRequest = LocationRequest.create().apply {
            interval = 3000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())

        task.addOnFailureListener { e ->
            val statusCode = (e as ResolvableApiException).statusCode
            if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                try {
                    e.startResolutionForResult(this, 100)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val GPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            if(!GPS){
                finish()
            }

            if (isGranted) {
                getMyLocation()
                getLocation()
            } else {
                Toast.makeText(this@MainActivity, "Lokasi tidak Diijinkan", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun getMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    getLocation(location)
                }
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocation(location: Location){
        Toast.makeText(this@MainActivity, "Lokasi latitude = ${location.latitude}, longitude = ${location.longitude}", Toast.LENGTH_SHORT).show()
    }

}