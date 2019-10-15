package de.adesso_mobile.coroutinesadvanced.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import de.adesso_mobile.coroutinesadvanced.R

class DummyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dummy_fragment, container, false)
    }

    companion object{
        fun newInstance() = DummyFragment()
    }

}
