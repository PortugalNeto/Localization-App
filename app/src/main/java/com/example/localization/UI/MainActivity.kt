package com.example.localization.UI


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.localization.R
import com.example.localization.model.Localizacao
import java.util.*

class MainActivity : AppCompatActivity(), LocationListener {

    val FINE_REQUEST = 12345
    val COARSE_REQUEST = 123
    var batidas = mutableListOf<Localizacao>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //-------------------------------------------------

        val btnObter = findViewById<Button>(R.id.btnObter)
        btnObter.setOnClickListener {

            this.obterLocalizacaoByGps()
            this.obterLocalizacaoByNetwork()
        }

    }

    override fun onLocationChanged(p0: Location) {

    }

    private fun obterLocalizacaoByNetwork() {

        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isNetEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (isNetEnabled) {

            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    0f,
                    this
                )

                var location: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                val lblLat = findViewById<TextView>(R.id.txtLat)
                lblLat.setText(location!!.latitude.toString())
                val lblLong = findViewById<TextView>(R.id.txtLong)
                lblLong.setText(location!!.longitude.toString())
            }
            else {
                this.requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), COARSE_REQUEST)
            }
        }
    }

    private fun obterLocalizacaoByGps() {

        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled) {

            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000L,
                    0f,
                    this
                )

                var location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val lblLat = findViewById<TextView>(R.id.txtLat)
                lblLat.setText(location!!.latitude.toString())
                val lblLong = findViewById<TextView>(R.id.txtLong)
                lblLong.setText(location!!.longitude.toString())

                var location1 = Localizacao(
                0, location.latitude.toString(), location.longitude.toString(), Date()
                )
                batidas.add(location1)
            }
            else {
                this.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), FINE_REQUEST)
            }
        }
    }
}