package com.example.threesidedcube.utils

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable


object Functions {
    fun getRegularFont(_context: Context): Typeface {
        return Typeface.createFromAsset(_context.assets, "fonts/regular.ttf")
    }

    fun getBoldFont(_context: Context): Typeface {
        return Typeface.createFromAsset(_context.assets, "fonts/bold.ttf")
    }

    /**
     * functions for show progressbar in imageview while loading from server
     */
    fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        return circularProgressDrawable
    }

    /**
     * checking internet connection
     */
    fun isInternetConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }


}

