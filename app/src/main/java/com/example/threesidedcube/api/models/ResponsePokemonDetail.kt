package com.example.threesidedcube.api.models

import com.google.gson.annotations.SerializedName


/**
 * from pokemon detail, only below data displayed:
 *  multiple images of pokemon displayed via pokemoon via sprites array
 *  base experience
 *  weight
 *  stats array is used for showing base analytics
 */

/*   sample response
{
    "sprites": {
    "back_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/25.png",
    "back_female": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/female/25.png",
    "back_shiny": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/25.png",
    "back_shiny_female": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/female/25.png",
    "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
    "front_female": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/female/25.png",
    "front_shiny": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/25.png",
    "front_shiny_female": "	"
},
    "height": 4,
    "base_experience": 112,
    "stats": [{
    "base_stat": 35,
    "effort": 0,
    "stat": {
    "name": "hp",
    "url": "https://pokeapi.co/api/v2/stat/1/"
}
}
    ],
    "weight": 60

}
*/


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
