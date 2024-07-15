package com.example.teraroon.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    val login = MutableLiveData<String>()
    val _login: LiveData<String> = login
    val pass = MutableLiveData<String>()
    val _pass: LiveData<String> = pass

    private val _text = MutableLiveData<String>().apply {
        value = "Кол-во нажатий: "
    }
    val text: LiveData<String> = _text
}