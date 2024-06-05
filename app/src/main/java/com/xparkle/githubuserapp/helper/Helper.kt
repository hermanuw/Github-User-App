package com.xparkle.githubuserapp.helper

import android.content.Context
import android.widget.Toast

object Helper {
    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}