package com.example.threesidedcube.api.models

import com.google.gson.annotations.SerializedName

data class ResponsePokeMonList(
    @SerializedName("next")
    val next: String = "",
    @SerializedName("previous")
    val previous: String = "",
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("results")
    val results: List<ResultsItem>?
)