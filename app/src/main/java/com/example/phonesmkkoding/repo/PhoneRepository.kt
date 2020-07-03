package com.example.phonesmkkoding.repo

import androidx.lifecycle.LiveData
import com.example.phonesmkkoding.dao.PhoneDao
import com.example.phonesmkkoding.model.PhoneModel

class PhoneRepository (private val phoneDao: PhoneDao) {

    val allPhone: LiveData<List<PhoneModel>> = phoneDao.getAllPhone()

    suspend fun insert(phone: PhoneModel) {
        phoneDao.insert(phone)
    }

    suspend fun insertAll(phone: List<PhoneModel>) {
        phoneDao.insertAll(phone)
    }

    suspend fun deleteAll() {
        phoneDao.deleteAll()
    }

    suspend fun update(phone: PhoneModel) {
        phoneDao.update(phone)
    }

    suspend fun delete(phone: PhoneModel) {
        phoneDao.delete(phone)
    }
}