package com.jetpack.xhb.video

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.jetpack.xhb.video.ui.fragment.HomeFragment


fun Fragment.showToast(context: String): Toast {
    val toast = Toast.makeText(App.context, context, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Context.showToast(context: String): Toast {
    val toast = Toast.makeText(App.context, context, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun View.dp2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}