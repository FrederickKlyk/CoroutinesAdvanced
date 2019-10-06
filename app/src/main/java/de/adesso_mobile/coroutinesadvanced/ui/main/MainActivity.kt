package de.adesso_mobile.coroutinesadvanced.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import de.adesso_mobile.coroutinesadvanced.R
import de.adesso_mobile.coroutinesadvanced.databinding.MainActivityBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    val viewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity).run {
            viewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_container_FL, TabContainerFragment())
            .commit()
    }
}
