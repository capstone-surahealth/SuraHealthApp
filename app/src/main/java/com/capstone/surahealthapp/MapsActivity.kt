package com.capstone.surahealthapp

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.capstone.surahealthapp.databinding.ActivityMapsBinding
import com.capstone.surahealthapp.utils.ResultState
import com.capstone.surahealthapp.view.common.ViewModelFactory
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: MapsViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var phone: String = ""
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private var nm_rs: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        // Inisialisasi FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupViewModel()
        val itemId = intent.getLongExtra("item_id", 0)
        showRumahSakit(itemId)
        checkGPS()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        val sydney = LatLng(lat, lng)
        mMap.addMarker(MarkerOptions().position(sydney).title(nm_rs))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f))
        getMyLocation()
    }

    private fun setupViewModel() {
        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MapsViewModel::class.java]
    }

    private fun showRumahSakit(id: Long) {
        lifecycleScope.launch {
            viewModel.rmState.collect { resultState ->
                when (resultState) {
                    is ResultState.Loading -> {

                    }
                    is ResultState.Success -> {
                        val rsDetail = resultState.data
                        binding.apply {
                            tvRs.text = rsDetail.nama_rm
                            nm_rs = rsDetail.nama_rm
                            Glide.with(binding.root)
                                .load(rsDetail.rm_photoUrl)
                                .fitCenter()
                                .into(ivPhotoRs)
                            phone = rsDetail.phone
                            lat = rsDetail.lat
                            lng = rsDetail.lng
                        }
                    }
                    is ResultState.Error -> {
                        val errorMessage = resultState.error
                        Toast.makeText(this@MapsActivity, errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        viewModel.getRumahSakitId(id)
    }

    private fun sendLocationToWhatsApp(location: Location) {
        val latitudeUser = location.latitude
        val longitudeUser = location.longitude
        binding.ibWa.setOnClickListener {
            if (phone.isNotEmpty()) {
                val mess =
                    "Permisi, Saya Butuh Ambulan \n Lokasi terkini: https://maps.google.com/?q=$latitudeUser,$longitudeUser"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=$mess")
                startActivity(intent)
            }
            Toast.makeText(this@MapsActivity, "Nomor ${phone}", Toast.LENGTH_SHORT).show()
        }
    }


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            } else {
                Toast.makeText(this@MapsActivity, "Lokasi tidak Diijinkan", Toast.LENGTH_SHORT)
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
                    showStartMarker(location)
                    sendLocationToWhatsApp(location)
                } else {
                    Toast.makeText(
                        this@MapsActivity,
                        "Location is not found. Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun showStartMarker(location: Location) {
        val startLocation = LatLng(location.latitude, location.longitude)
        mMap.addMarker(
            MarkerOptions()
                .position(startLocation)
                .title(getString(R.string.user))
        )
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 17f))
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
}