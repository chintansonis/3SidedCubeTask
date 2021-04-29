package com.example.threesidedcube.utils

import android.content.Context
import android.graphics.Typeface


object Functions {
    fun getRegularFont(_context: Context): Typeface {
        return Typeface.createFromAsset(_context.assets, "fonts/regular.ttf")
    }

    fun getBoldFont(_context: Context): Typeface {
        return Typeface.createFromAsset(_context.assets, "fonts/bold.ttf")
    }


}

