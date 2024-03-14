package com.slowit.core.extensions

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast

fun Context.isLandscape() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Context.showMessage(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
