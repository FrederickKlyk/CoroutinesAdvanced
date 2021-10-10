package de.klyk.coroutinesadvanced.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("visibility")
    fun View.bindBooleanToVisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("errorEnabled")
    fun TextInputLayout.bindErrorEnabled(isError: Boolean) {
        isErrorEnabled = isError
    }

    @JvmStatic
    @BindingAdapter("errorText")
    fun TextInputLayout.errorText(errorText: String?) {
        error = errorText
    }
}