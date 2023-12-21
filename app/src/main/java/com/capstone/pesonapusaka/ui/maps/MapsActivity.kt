package com.capstone.pesonapusaka.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.data.model.Location
import com.capstone.pesonapusaka.databinding.ActivityMapsBinding
import com.capstone.pesonapusaka.utils.Dimens
import com.capstone.pesonapusaka.utils.Dimens.NAMA_LOKASI
import com.capstone.pesonapusaka.utils.Result
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var _binding: ActivityMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private val boundsBuilder = LatLngBounds.Builder()
    private val viewModel by viewModels<MapsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMap()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun checkLocationIntent() {
        val location = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Dimens.LOCATION, Location::class.java)
        } else {
            intent.getParcelableExtra(Dimens.LOCATION)
        }
        val namaLokasi = intent.getStringExtra(NAMA_LOKASI)

        location?.let {
            val latLng = LatLng(it.lat, it.lng)
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .snippet(namaLokasi ?: "")
            )
            boundsBuilder.include(latLng)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
            mMap.animateCamera(cameraUpdate)
        }
    }

    private fun setupMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.maps_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
        getMyLocation()
        checkLocationIntent()
        observer()
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun observer() {
        val candi = viewModel.candiRecommendation.asLiveData()
        candi.observe(this) { result ->
            when(result) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> {
                    result.data?.let { data ->
                        data.forEach { candi ->
                            candi.latitude?.let {
                                if(data.isNotEmpty()){
                                    val latLng = LatLng(candi.latitude, candi.longitude!!)
                                    mMap.addMarker(
                                        MarkerOptions()
                                            .position(latLng)
                                            .title(candi.name)
                                            .snippet(candi.location)
                                    )
                                    boundsBuilder.include(latLng)
                                }
                            }
                        }
                    }
                }
                is Result.Void -> {}
            }
        }

        val umkm = viewModel.wisataKuliner.asLiveData()
        umkm.observe(this) { result ->
            when(result) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> {
                    result.data?.let { data ->
                        data.forEach { kuliner ->
                            kuliner.latitude?.let {
                                if(data.isNotEmpty()){
                                    val latLng = LatLng(kuliner.latitude, kuliner.longitude!!)
                                    mMap.addMarker(
                                        MarkerOptions()
                                            .position(latLng)
                                            .title(kuliner.name)
                                            .snippet(kuliner.location)
                                    )
                                    boundsBuilder.include(latLng)
                                }
                            }
                        }
                    }
                }
                is Result.Void -> {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}