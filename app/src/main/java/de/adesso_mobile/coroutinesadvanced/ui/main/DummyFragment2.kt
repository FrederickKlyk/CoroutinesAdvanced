package de.adesso_mobile.coroutinesadvanced.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.adesso_mobile.coroutinesadvanced.R
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseFragment
import timber.log.Timber

class DummyFragment2 : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myArg = arguments?.get("test")
        Timber.d("my Arg: $myArg")
        return inflater.inflate(R.layout.dummy2_fragment, container, false)
    }
}
