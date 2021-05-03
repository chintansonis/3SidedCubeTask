package com.example.threesidedcube.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.threesidedcube.R
import com.example.threesidedcube.api.models.ResultsItem
import com.example.threesidedcube.utils.AppConstants
import com.example.threesidedcube.utils.Functions
import kotlinx.android.synthetic.main.item_pokemon_list.view.*

class ViewPagerAdapter(private val context: Context, private var imagList: List<String>) :
    PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null
    override fun getCount(): Int {
        return imagList.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.item_viewpager, null)
        val imgPokeMon =
            view.findViewById<View>(R.id.imgPokeMon) as AppCompatImageView
        Glide.with(context).load(imagList[position]).placeholder(
            Functions.getCircularProgressDrawable(context)
        ).into(imgPokeMon)
        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }

}