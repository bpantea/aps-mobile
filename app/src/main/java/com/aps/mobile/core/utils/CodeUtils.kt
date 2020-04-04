package com.aps.mobile.core.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun showLongText(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun startActivityAsNewTask(context: Context, activity: Class<*>) {
    val intent = Intent(context, activity)
    intent.flags += Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}
