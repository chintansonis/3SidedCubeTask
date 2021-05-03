package com.example.threesidedcube.api.models

import com.google.gson.annotations.SerializedName

data class ResponsePokemonDetail(@SerializedName("stats")
                                 val stats: List<StatsItem>?,
                                 @SerializedName("forms")
                                 val forms: List<FormsItem>?,
                                 @SerializedName("base_experience")
                                 val baseExperience: Int = 0,
                                 @SerializedName("weight")
                                 val weight: Int = 0,
                                 @SerializedName("sprites")
                                 val sprites: Sprites,
                                 @SerializedName("height")
                                 val height: Int = 0)
