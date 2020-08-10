package de.klyk.coroutinesadvanced.ui.main

import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.databinding.MainActivityBinding
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity(), AppBarConfiguration.OnNavigateUpListener {

    val viewModel: MainActivityViewModel by inject()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity).run {
            viewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }

        navController = findNavController(R.id.nav_host_fragment)
        setupNavigation(navController)

        //hear for event changes
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }
            Toast.makeText(this@MainActivity, "Navigated to $dest", Toast.LENGTH_SHORT).show()
            Timber.d("NavigationActivity: Navigated to $dest")
        }
    }

    private fun setupNavigation(navController: NavController) {
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerMain_DL)
        findViewById<NavigationView>(R.id.navigation_view).setupWithNavController(navController)

        val drawerLayout: DrawerLayout? = findViewById(R.id.drawerMain_DL)
        //it means that they will remain on the backstack after navigating to one another. That’s called placing as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.menu_coroutines, R.id.menu_overviewLibs),
            drawerLayout
        )

        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(
            navController,
            appBarConfiguration
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment)) || super.onOptionsItemSelected(
            item
        )
    }

    override fun onBackPressed() {
        if (drawerMain_DL.isDrawerOpen(GravityCompat.START)) {
            drawerMain_DL.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
