package de.adesso_mobile.coroutinesadvanced.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.navigation.NavigationView
import de.adesso_mobile.coroutinesadvanced.R
import de.adesso_mobile.coroutinesadvanced.databinding.MainActivityBinding
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val viewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity).run {
            viewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }

        val toggle = ActionBarDrawerToggle(
            this, drawerMain_DL, toolbar, 0, 0
        )
        drawerMain_DL.addDrawerListener(toggle)
        toggle.syncState()
        navigation_view.setNavigationItemSelectedListener(this)


        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_container_FL, TabContainerFragment())
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_coroutines -> {
                Toast.makeText(this, "Coroutines", Toast.LENGTH_SHORT).show()
            }
        }
        drawerMain_DL.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onBackPressed() {
        if (drawerMain_DL.isDrawerOpen(GravityCompat.START)) {
            drawerMain_DL.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
