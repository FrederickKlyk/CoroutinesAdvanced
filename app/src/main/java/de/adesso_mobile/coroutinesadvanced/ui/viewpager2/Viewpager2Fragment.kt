package de.adesso_mobile.coroutinesadvanced.ui.viewpager2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import de.adesso_mobile.coroutinesadvanced.R

/**
 * A simple [Fragment] subclass.
 */
class Viewpager2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_viewpager2, container, false)
    }


}
