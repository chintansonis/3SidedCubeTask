package com.example.threesidedcube.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.threesidedcube.R
import com.example.threesidedcube.api.models.ResultsItem
import com.example.threesidedcube.utils.AppConstants
import com.example.threesidedcube.utils.Functions
import kotlinx.android.synthetic.main.item_pokemon_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class PokeMonRecyclerAdapter(
    private val activity: AppCompatActivity?,
    private var resultItemList: List<ResultsItem>
) : RecyclerView.Adapter<PokeMonRecyclerAdapter.PokeMonRecyclerAdapterVH>(),Filterable {
    private var resultItemFilterList: List<ResultsItem> = emptyList()

    // Allows to remember the last item shown on screen
    private var lastPosition = -1

    init {
        resultItemFilterList = resultItemList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeMonRecyclerAdapterVH {
        val view: View = LayoutInflater.from(activity).inflate(
            R.layout.item_pokemon_list,
            parent,
            false
        )
        return PokeMonRecyclerAdapterVH(view, viewType)
    }

    override fun onBindViewHolder(holder: PokeMonRecyclerAdapterVH, position: Int) {
        holder.itemView.txtPokeMonName.text = resultItemFilterList[position].name
        /**
         * in order to load images we have pokemon api for images by appending pokemon id which we receive from enpoint api
         * example https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/21.png where 21 is id of pokemon
         *
         */
        val splittedPokemonImageID=resultItemFilterList[position].url.substring(resultItemFilterList[position].url.lastIndexOf("/")-2)
        Glide.with(activity!!).load(AppConstants.getBaseImageUrl()+"/"+splittedPokemonImageID.replace("/","")+".png").placeholder(Functions.getCircularProgressDrawable(activity)).into(holder.itemView.imgPokeMon)
        holder.itemView.setOnClickListener {

        }
        setAnimation(holder.itemView, position)
    }

    /**
     * Here is the key method to apply the animation
     */
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount(): Int {
        return resultItemFilterList.size
    }


    /**
     * notify adapter by updated data
     */
    fun notifyupdatedPokeMonList(arrayList: ArrayList<ResultsItem>) {
        resultItemList = ArrayList()
        resultItemList = arrayList
        resultItemFilterList=resultItemList
        notifyDataSetChanged()
    }
    class PokeMonRecyclerAdapterVH(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView)


    /**
     * perfrom search filtering in recyclerview and publish results
     */
    override fun getFilter(): android.widget.Filter {
        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    resultItemFilterList = resultItemList
                } else {
                    val resultList = ArrayList<ResultsItem>()
                    for (row in resultItemList) {
                        if (row.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultItemFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = resultItemFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                resultItemFilterList = results?.values as ArrayList<ResultsItem>
                notifyDataSetChanged()
            }


        }
    }
}