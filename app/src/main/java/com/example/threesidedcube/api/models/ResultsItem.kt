package com.example.threesidedcube.api.models

import com.google.gson.annotations.SerializedName

data class ResultsItem(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)