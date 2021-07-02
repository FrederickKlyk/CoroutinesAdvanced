package de.klyk.dummy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dummy.databinding.MainDummyFragmentBinding


class MainDummyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return MainDummyFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@MainDummyFragment
        }.root
    }

}