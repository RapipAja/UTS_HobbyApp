package com.ubaya.hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ubaya.hobbyapp.model.User

class UserViewModel(application: Application): AndroidViewModel(application) {
    val userLD = MutableLiveData<User>()
    val loginLD = MutableLiveData<Boolean>()
    val createLD = MutableLiveData<Boolean>()
    val updateLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun loginCheck(username:String, password:String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/UTS_ANMP/HobbyApp/login.php"

        val stringRequest = object : StringRequest(
            Method.POST, url, { response->
                try {
                    val logUser = Gson().fromJson(response, User::class.java)

                    if (logUser == null || logUser.id.isNullOrEmpty()){
                        loginLD.value = false
                    } else {
                        userLD.value = logUser
                        loginLD.value = true
                    }
                } catch (e: Exception){
                    loginLD.value = false
                    Toast.makeText(getApplication(), "Login Successful", Toast.LENGTH_SHORT).show()
                    Log.e("Success", "Response: ${response}", e)
                }
            }, {
                loginLD.value = false
                Toast.makeText(getApplication(), "Login Failed", Toast.LENGTH_SHORT).show()
                Log.e("login error", "Error: ${it.message}", it)
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }


    fun createUser(username:String, firstName:String, lastName:String, password:String, picture:String) {
        queue = Volley.newRequestQueue(getApplication())
        //IP Change
        val url = "http://10.0.2.2/UTS_ANMP/HobbyApp/register.php"

        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                createLD.value = true
                Toast.makeText(getApplication(), "Register Successful", Toast.LENGTH_SHORT).show()
                Log.d("Success", "Response: ${response}")
            }, {
                createLD.value = false
//                Toast.makeText(getApplication(), "Register Failed", Toast.LENGTH_SHORT).show()
                Log.d("Register error", it.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = username
                params["firstname"] = firstName
                params["lastname"] = lastName
                params["password"] = password
                params["picture"] = picture
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun changeUser(id:String, firstName: String, lastName: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        //IP Change
        val url = "http://10.0.2.2/UTS_ANMP/HobbyApp/change_user.php"

        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                updateLD.value = true
                Toast.makeText(getApplication(), "Update Successful", Toast.LENGTH_SHORT).show()
                Log.d("Success", "Response: ${response}")
            }, {
                updateLD.value = false
//                Toast.makeText(getApplication(), "Update Failed", Toast.LENGTH_SHORT).show()
                Log.d("Update error", it.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = id
                params["firstname"] = firstName
                params["lastname"] = lastName
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}