package com.mandiri.gamesapp.extension

import android.widget.Toast
import com.mandiri.gamesapp.GamesApplication

fun showToast(message: String) {
    Toast.makeText(GamesApplication.instance, message, Toast.LENGTH_LONG).show()
}