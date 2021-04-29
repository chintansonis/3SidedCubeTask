package com.example.threesidedcube.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.threesidedcube.R
import com.example.threesidedcube.utils.Functions


open class ThreeSidedCubeCustomTextView : AppCompatTextView {

    private var _ctx: Context? = null
    private var isBold: Boolean = false

    constructor(context: Context) : super(context) {
        if (!isInEditMode) {
            this._ctx = context
            init()
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        if (!isInEditMode) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.ThreeSidedCubeCustomTextView, 0, 0)
            try {
                isBold = a.getBoolean(R.styleable.ThreeSidedCubeCustomTextView_isBold, false)
            } finally {
                a.recycle()
            }
            this._ctx = context
            init()
        }
    }

    fun setBold(isBold: Boolean) {
        this.isBold = isBold
        if (isBold) {
            typeface = _ctx?.let { Functions.getBoldFont(it) }
        } else {
            typeface = _ctx?.let { Functions.getRegularFont(it) }
        }
    }

    private fun init() {
        try {
            if (isBold) {
                typeface = _ctx?.let { Functions.getBoldFont(it) }
            } else {
                typeface = _ctx?.let { Functions.getRegularFont(it) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
