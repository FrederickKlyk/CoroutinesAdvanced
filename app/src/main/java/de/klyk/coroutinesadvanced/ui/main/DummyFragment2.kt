package de.klyk.coroutinesadvanced.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.ui.base.BaseFragment
import timber.log.Timber

class DummyFragment2 : BaseFragment() {
    val args: DummyFragment2Args by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myArg = arguments?.get("test")
        val testArg = arguments?.get("test2")
        val message = arguments?.get("message")
        Timber.d("my Arg: $myArg")
        Timber.d("my Arg2: $testArg")
        Timber.d("my message: $message")
        return inflater.inflate(R.layout.dummy2_fragment, container, false)
    }
}
