package com.example.phonesmkkoding.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.phonesmkkoding.db.PhoneDatabase
import com.example.phonesmkkoding.model.PhoneModel
import com.example.phonesmkkoding.repo.PhoneRepository
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PhoneViewModel: ViewModel() {
    lateinit var repository: PhoneRepository

    fun init(context: Context) {
        val phoneDao = PhoneDatabase.getDatabase(context).phoneDao()
        repository = PhoneRepository(phoneDao)
    }

    fun addData(phone: PhoneModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(phone)
    }
}