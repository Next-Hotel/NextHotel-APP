package com.nexthotel.core.utils

import android.content.Context
import android.widget.Toast
import com.nexthotel.R

object Utils {
    fun toast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}