package com.example.phonesmkkoding.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonesmkkoding.db.PhoneDatabase
import com.example.phonesmkkoding.model.PhoneModel
import com.example.phonesmkkoding.repo.PhoneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhoneActivityViewModel : ViewModel() {
    lateinit var repository: PhoneRepository

    lateinit var allPhone: LiveData<List<PhoneModel>>

    fun init(context: Context) {
        val phoneDao = PhoneDatabase.getDatabase(context).phoneDao()
        repository = PhoneRepository(phoneDao)
        allPhone = repository.allPhone
    }

    fun delete(phone: PhoneModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(phone)
    }

    fun insertAll(phone: List<PhoneModel>) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
        repository.insertAll(phone)
    }
}