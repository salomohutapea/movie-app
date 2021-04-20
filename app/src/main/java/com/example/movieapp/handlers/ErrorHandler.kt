package com.example.movieapp.handlers

import android.content.Context
import android.widget.Toast
import com.example.movieapp.R

class ErrorHandler {
    fun errorMessage(code: Int, context: Context) {
        if (code in 400..443 && code in 445..598) {
            Toast.makeText(context, "$this ${context.getText(R.string.cannot_fetch)}", Toast.LENGTH_SHORT).show()
        } else if (code == 444) {
            Toast.makeText(context, context.getString(R.string.no_connection), Toast.LENGTH_SHORT).show()
        }
    }
}