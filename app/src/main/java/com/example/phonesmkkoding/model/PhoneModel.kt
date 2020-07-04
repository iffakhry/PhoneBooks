package com.example.phonesmkkoding.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phone")
data class PhoneModel(
    var nama: String,
    var alamat: String,
    var no_telp: String,
    var jenis: String,
    var kota: String,
    var provinsi: String,
    var latitude: String,
    var longitude: String,
    @PrimaryKey var key: String
){
    constructor(): this("", "", "", "", "", "", "", "", "0")
}