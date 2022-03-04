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
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.databinding.MainActivityBinding
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity(), AppBarConfiguration.OnNavigateUpListener {
    var binding: MainActivityBinding? = null

    val viewModel: MainActivityViewModel by inject()
    val sharedViewModel: MainSharedViewModel by inject()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
            .apply {
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupNavigation(navController: NavController) {
        appBarConfiguration = AppBarConfiguration(navController.graph, binding?.drawerMainDL)

        val drawerLayout: DrawerLayout? = findViewById(R.id.drawerMain_DL)
        //Next, connect the DrawerLayout to your navigation graph by passing it to AppBarConfiguration, as shown in the following example
        //it means that they will remain on the backstack after navigating to one another. That’s called placing as top level destinations.
        //Note: When using NavigationUI, the top app bar helpers automatically transition between the drawer
        // icon and the Up icon as the current destination changes. You don't need to use ActionBarDrawerToggle.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.menu_coroutines, R.id.menu_overviewLibs, R.id.dummy_nav_graph),
            drawerLayout
        )

        binding?.bottomNavigationView?.let {
            NavigationUI.setupWithNavController(
                it,
                navController
            )
        }
        binding?.navigationView?.let { NavigationUI.setupWithNavController(it, navController) }
        //Note: When using a Toolbar, Navigation automatically handles click events for the Navigation button, so you do not need to override onSupportNavigateUp().
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(
            navController,
            appBarConfiguration
        )
    }

    /**
     * If your menu was added via the Activity's onCreateOptionsMenu(), for example, you can associate
     * the menu items with destinations by overriding the Activity's onOptionsItemSelected() to call onNavDestinationSelected(),
     * as shown in the following example:
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment)) ||
                super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding?.drawerMainDL?.isDrawerOpen(GravityCompat.START) == true) {
            binding?.drawerMainDL?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
