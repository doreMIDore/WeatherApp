package com.doremidore.weatherapp.model

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(p: String) : Boolean{
    return ContextCompat.checkSelfPermission(
        activity as AppCompatActivity, p) == PackageManager.PERMISSION_GRANTED
}

fun Activity.isPermissionGranted(p: String) : Boolean{
    return ContextCompat.checkSelfPermission(this, p) == PackageManager.PERMISSION_GRANTED
}