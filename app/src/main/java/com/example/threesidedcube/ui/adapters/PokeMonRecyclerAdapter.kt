package com.example.threesidedcube.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.threesidedcube.R
import com.example.threesidedcube.api.models.ResultsItem
import com.example.threesidedcube.utils.AppConstants
import com.example.threesidedcube.utils.Functions
import kotlinx.android.synthetic.main.item_pokemon_list.view.*

class PokeMonRecyclerAdapter(
    private val activity: AppCompatActivity?,
    private var resultItemList: List<ResultsItem>
) : RecyclerView.Adapter<PokeMonRecyclerAdapter.PokeMonRecyclerAdapterVH>() {
    // Allows to remember the last item shown on screen
    private var lastPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeMonRecyclerAdapterVH {
        val view: View = LayoutInflater.from(activity).inflate(
            R.layout.item_pokemon_list,
            parent,
            false
        )
        return PokeMonRecyclerAdapterVH(view, viewType)
    }

    override fun onBindViewHolder(holder: PokeMonRecyclerAdapterVH, position: Int) {
        holder.itemView.txtPokeMonName.text = resultItemList[position].name
        /**
         * in order to load images we have pokemon api for images by appending pokemon id which we receive from enpoint api
         * example https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/21.png where 21 is id of pokemon
         *
         */
        val splittedPokemonImageID=resultItemList[position].url.substring(resultItemList[position].url.lastIndexOf("/")-2)
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
        return resultItemList.size
    }


    /**
     * notify adapter by updated data
     */
    fun notifyupdatedPokeMonList(arrayList: ArrayList<ResultsItem>) {
        resultItemList = ArrayList()
        resultItemList = arrayList
        notifyDataSetChanged()
    }
    class PokeMonRecyclerAdapterVH(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView)
}