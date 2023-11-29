package com.example.lesson_3_zhuravleva.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.Manifest
import androidx.core.view.isVisible
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.databinding.ActivityMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.GeoObjectSelectionMetadata
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.image.ImageProvider

class MapActivity : AppCompatActivity() {

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMapBinding

    private val mapTapListener = GeoObjectTapListener {
        val selectionMetadata: GeoObjectSelectionMetadata = it
            .geoObject
            .metadataContainer
            .getItem(GeoObjectSelectionMetadata::class.java)
        binding.mapView.mapWindow.map.selectGeoObject(selectionMetadata)

        if (it.geoObject.name != null){
            binding.apply{
                btnChoose.isEnabled = true
                tvAddress.isVisible = true
                tvAddress.text = it.geoObject.name
            }
        } else {
            binding.apply{
                btnChoose.isEnabled = false
                tvAddress.isVisible = false
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(this)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.mapWindow.map.move(
            CameraPosition(
                Point(59.935493, 30.327392),
                /* zoom = */ 17.0f,
                /* azimuth = */ 0.0f,
                /* tilt = */ 0.0f
            ),
            Animation(Animation.Type.LINEAR, 3f),
            null,
        )

        binding.apply{
            mapView.mapWindow.map.addTapListener(mapTapListener)
            btnClose.setOnClickListener {
                finish()
            }
            btnChoose.setOnClickListener {
                val address = Intent()
                address.putExtra("address", binding.tvAddress.text)
                setResult(RESULT_OK, address)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}