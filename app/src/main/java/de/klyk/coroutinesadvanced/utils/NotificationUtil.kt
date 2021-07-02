package de.klyk.coroutinesadvanced.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object NotificationUtil {

    internal val DEFAULT_DURATION = 5000

    fun createSnackBar(root: View, description: String): Snackbar {
        return Snackbar.make(root, description, Snackbar.LENGTH_LONG).apply {
            duration = DEFAULT_DURATION
        }
    }
}