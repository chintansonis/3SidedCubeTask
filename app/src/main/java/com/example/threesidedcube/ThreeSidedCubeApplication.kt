package com.example.threesidedcube

import androidx.multidex.MultiDexApplication

class ThreeSidedCubeApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {

    }
}