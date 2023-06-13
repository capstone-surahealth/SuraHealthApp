package com.capstone.surahealthapp.view.rumahsakit

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.surahealthapp.databinding.ActivityRumahSakitBinding
import com.capstone.surahealthapp.utils.ResultState
import com.capstone.surahealthapp.view.common.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class RumahSakitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRumahSakitBinding
    private lateinit var viewModel: RumahSakitViewModel
    private lateinit var adapter: RumahSakitAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRumahSakitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Rumah Sakit"

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        adapter = RumahSakitAdapter()
        binding.rvRumahSakit.layoutManager = LinearLayoutManager(this)
        binding.rvRumahSakit.adapter = adapter
        setupViewModel()
        getMyLocation()
    }

    private fun setupViewModel() {
        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[RumahSakitViewModel::class.java]
    }

    private fun getMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    getLocation(location)
                    viewModel.getRumahSakitTerdekat(location.longitude, location.latitude).observe(this){ result ->
                        when(result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }
                            is ResultState.Success -> {
                                showLoading(false)
                                val rs = result.data
                                adapter.submitList(rs)
                            }
                            is ResultState.Error -> {

                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        this@RumahSakitActivity,
                        "Lokasi tidak ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getLocation(location: Location){
        Toast.makeText(this@RumahSakitActivity, "Lokasi latitude = ${location.latitude}, longitude = ${location.longitude}", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}