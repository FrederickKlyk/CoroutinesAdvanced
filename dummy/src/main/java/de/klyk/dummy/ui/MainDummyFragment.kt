package de.klyk.dummy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.dummy.R
import com.example.dummy.databinding.MainDummyFragmentBinding


internal class MainDummyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MainDummyFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@MainDummyFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.dummy_nav_module_to_dummy2_button)

        button.setOnClickListener {
            findNavController().navigate(MainDummyFragmentDirections.actionMainDummyFragmentToSecondDummyFragment())
        }
    }
}