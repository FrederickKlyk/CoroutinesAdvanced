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
import com.google.android.material.navigation.NavigationBarMenu
import com.google.android.material.navigation.NavigationBarMenuView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.databinding.MainActivityBinding
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.tab_container_fragment.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity(), AppBarConfiguration.OnNavigateUpListener {

    val viewModel: MainActivityViewModel by inject()
    val sharedViewModel: MainSharedViewModel by inject()
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

        val drawerLayout: DrawerLayout? = findViewById(R.id.drawerMain_DL)
        //Next, connect the DrawerLayout to your navigation graph by passing it to AppBarConfiguration, as shown in the following example
        //it means that they will remain on the backstack after navigating to one another. That’s called placing as top level destinations.
        //Note: When using NavigationUI, the top app bar helpers automatically transition between the drawer
        // icon and the Up icon as the current destination changes. You don't need to use ActionBarDrawerToggle.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.menu_coroutines, R.id.menu_overviewLibs, R.id.dummy_nav_graph),
            drawerLayout
        )

        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        NavigationUI.setupWithNavController(navigationView, navController)
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
        if (drawerMain_DL.isDrawerOpen(GravityCompat.START)) {
            drawerMain_DL.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
