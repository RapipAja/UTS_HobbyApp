package com.ubaya.hobbyapp.model

import com.google.gson.annotations.SerializedName

data class User(
    var id:String?,
    var username:String?,
    var firstname:String?,
    var lastname:String?,
    var password:String?,
    var picture:String?,
)


data class Hobby(
    var id:String?,
    var image_url:String?,
    var title:String?,
    var author:String?,
    var description:String?,
    var date_published:String?,
    var content:List<String>?,
)