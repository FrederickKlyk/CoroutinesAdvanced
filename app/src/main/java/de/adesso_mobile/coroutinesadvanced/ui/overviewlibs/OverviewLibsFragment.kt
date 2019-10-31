package de.adesso_mobile.coroutinesadvanced.ui.overviewlibs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.adesso_mobile.coroutinesadvanced.databinding.OverviewLibsFragmentBinding
import org.koin.android.ext.android.inject


class OverviewLibsFragment : Fragment() {

    val viewModel: OverviewLibsFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return OverviewLibsFragmentBinding.inflate(inflater).apply {
            viewModel = this@OverviewLibsFragment.viewModel
            lifecycleOwner = this@OverviewLibsFragment
        }.root
    }
}
