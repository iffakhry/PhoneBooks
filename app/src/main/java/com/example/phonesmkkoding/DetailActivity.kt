package com.example.phonesmkkoding

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        getData()

        btn_maps.setOnClickListener { toMaps() }
        img_call.setOnClickListener {directCall()}
    }

    private fun toMaps(){
        val latitude = intent?.getStringExtra("latitude").toString()
        val longitude = intent?.getStringExtra("longitude").toString()
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(packageManager)?.let {
            startActivity(mapIntent)
        }
    }
    private fun getData() {
        val getPhone: String = intent?.getStringExtra("notelp").toString()
        val getNama: String = intent?.getStringExtra("nama").toString()
        val getAlamat: String = intent?.getStringExtra("alamat").toString()
        val getJenis: String = intent?.getStringExtra("jenis").toString()
        tv_nama.text = getNama
        tv_address.text = getAlamat
        tv_phonenumber.text = getPhone
        if(getJenis == "Listrik"){
            img_initial.setImageResource(R.drawable.initial_icon_electric)
        }
        else if(getJenis == "Kesehatan" || getJenis == "Emergency"){
            img_initial.setImageResource(R.drawable.heal)
        }
        else if (getJenis == "Transportasi"){
            img_initial.setImageResource(R.drawable.transport)
        }
        else {
            img_initial.setImageResource(R.drawable.bulding)
        }
    }
    private fun directCall(){
        val getPhone: String = intent?.getStringExtra("notelp").toString()
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$getPhone"))
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        startActivity(intent)
    }
}