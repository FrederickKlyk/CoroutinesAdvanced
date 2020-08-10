package de.klyk.coroutinesadvanced.utils

import androidx.lifecycle.LiveData
import timber.log.Timber

class MutableLiveDataNotNull<T>(private val defaultValue: T) : LiveData<T>(defaultValue) {
    override fun getValue(): T = super.getValue()?.let { it } ?: defaultValue.apply {
        Timber.e("Es wurde für das LiveData Objekt das defaultValue $this gesetzt.")
    }
    public override fun setValue(value: T) = super.setValue(value)
    public override fun postValue(value: T) = super.postValue(value)
}