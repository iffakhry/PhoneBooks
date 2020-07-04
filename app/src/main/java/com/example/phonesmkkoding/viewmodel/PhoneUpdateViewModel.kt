package com.example.phonesmkkoding.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonesmkkoding.db.PhoneDatabase
import com.example.phonesmkkoding.model.PhoneModel
import com.example.phonesmkkoding.repo.PhoneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhoneUpdateViewModel: ViewModel() {

    lateinit var repository: PhoneRepository

    fun init(context: Context) {
        val phoneDao = PhoneDatabase.getDatabase(context).phoneDao()
        repository = PhoneRepository(phoneDao)
    }

    fun updateData(phone: PhoneModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(phone)
    }

}