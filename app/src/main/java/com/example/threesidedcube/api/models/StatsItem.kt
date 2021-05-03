package com.example.threesidedcube.api.models

import com.google.gson.annotations.SerializedName

data class StatsItem(@SerializedName("stat")
                     val stat: Stat,
                     @SerializedName("base_stat")
                     val baseStat: Int = 0,
                     @SerializedName("effort")
                     val effort: Int = 0)