package de.adesso_mobile.coroutinesadvanced.ui.overviewlibs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.adesso_mobile.coroutinesadvanced.databinding.OverviewLibsFragmentBinding
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseFragment
import org.koin.android.ext.android.inject


class OverviewLibsFragment : BaseFragment() {

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
