package de.klyk.dummy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dummy.R
import com.example.dummy.databinding.MainDummyFragmentBinding
import com.example.dummy.databinding.SecondDummyFragmentBinding


internal class SecondDummyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return SecondDummyFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@SecondDummyFragment
        }.root
    }
}