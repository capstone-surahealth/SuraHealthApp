package com.capstone.surahealthapp.view.maps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.capstone.surahealthapp.R
import com.capstone.surahealthapp.data.response.RumahSakitResponse
import com.capstone.surahealthapp.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var phone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Maps Rumah Sakit"

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        // Inisialisasi FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        getMyLocation()
        showRumahSakit()
    }
    private fun showRumahSakit() {
        val rs = intent.getParcelableExtra<RumahSakitResponse>(EXTRA_KODE_RS)
        binding.apply {
            if (rs != null){
                tvRs.text = rs.namaRumahSakit
                tvKelas.text = rs.kelas
                tvTipe.text = rs.tipe
                tvBed.text = rs.jumlahBed.toString()
                tvBpjs.text = rs.bpjs.toString()
                phone = rs.wa.toString()

                Glide.with(binding.root)
                    .load(rs.foto)
                    .fitCenter()
                    .into(ivPhotoRs)

                val lok = LatLng(rs.latitude!!, rs.longitude!!)
                mMap.addMarker(MarkerOptions().position(lok).title(rs.alamat))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lok, 16f))

                Log.d("Lokasi = ", "${rs.latitude!!}  ${rs.longitude!!}")
            }
        }
    }

    private fun sendLocationToWhatsApp(location: Location) {
        val latitudeUser = location.latitude
        val longitudeUser = location.longitude
        binding.ibWa.setOnClickListener {
            if (phone.isNotEmpty()) {
                val mess =
                    "Permisi, Saya saat ini membutuhkan bantuan dari rumah sakit, \nLokasi saya: https://maps.google.com/?q=$latitudeUser,$longitudeUser"
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
                        "Lokasi tidak ditemukan",
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
    }

    companion object {
        const val EXTRA_KODE_RS = "extra_kode_rs"
    }
}