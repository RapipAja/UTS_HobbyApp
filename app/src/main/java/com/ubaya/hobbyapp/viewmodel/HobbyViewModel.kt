package com.ubaya.hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.hobbyapp.model.Hobby

class HobbyViewModel(application: Application): AndroidViewModel(application) {
    var hobbyLD = MutableLiveData<ArrayList<Hobby>>()
    var hobbyDetailLD = MutableLiveData<Hobby>()
    val hobbyLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun refresh() {
        loadingLD.value = true
        hobbyLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/UTS_ANMP/HobbyApp/hobby.json"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                val type = object : TypeToken<ArrayList<Hobby>>() {}.type
                val result = Gson().fromJson<List<Hobby>>(it, type)
                hobbyLD.value = result as ArrayList<Hobby>?

                Log.d("Success", it)
                loadingLD.value = false
            },{
                loadingLD.value = false
                hobbyLoadErrorLD.value = false
                Log.d("load error", it.toString())
            }
        )

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun detailHobby(id:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/UTS_ANMP/HobbyApp/detail.php?id=${id}"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                hobbyDetailLD.value = Gson().fromJson(it, Hobby::class.java)

                Log.d("Success", it)
            }, {
                Log.d("Error", it.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}