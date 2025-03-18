package com.example.student.viewmodel

import android.app.Application
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.student.model.Student
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListViewModel(app:Application): AndroidViewModel(app) {
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()
    val TAG = "student data fetch"
    private var queue:RequestQueue? = null

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

    fun refresh() {
        loadingLD.value = true
        errorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        var url = "https://www.jsonkeeper.com/b/LLMW"

        val sr = StringRequest(
            Request.Method.GET, url,
            {
                loadingLD.value = false
                errorLD.value = false

                val sType = object: TypeToken<List<Student>>() {}.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                studentsLD.value = result as ArrayList<Student>
            },
            {
                errorLD.value = true
                loadingLD.value = false
            }
        )

        sr.tag = TAG
        queue?.add(sr)


    }
}