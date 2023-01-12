package com.mandiri.gamesapp.extension

import android.os.SystemClock
import android.view.View

inline fun View.setOnClickWithThrottle(
    threshold: Long = 1000L,
    crossinline action: (v: View?) -> Unit
) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View?) {
            if (SystemClock.elapsedRealtime() - lastClickTime < threshold) {
                return
            } else action(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.showOrHide(isShow: Boolean) {
    this.visibility = if (isShow) View.VISIBLE else View.GONE
}