package com.example.navigationdrawerkotlin
import CategoriasFragment
import CuentaFragment
import MisproductosFragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, homeFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_mapa
            -> {
                showMapFragment()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showMapFragment() {
        val mapFragment = MapsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mapFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, homeFragment())
                .commit()
            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, settingsFragment())
                .commit()
            R.id.nav_categorias -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CategoriasFragment())
                .commit()
            R.id.nav_logout -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LogoutFragment())
                .commit()
            R.id.nav_busqueda -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BuscarFragment())
                .commit()
            R.id.nav_quejas -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PqrsFragment())
                .commit()
            R.id.nav_misproductos -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MisproductosFragment())
                .commit()
            R.id.nav_cuenta -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CuentaFragment())
                .commit()
            R.id.nav_cart -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CarritoFragment())
                .commit()
            R.id.nav_mapa -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MapsFragment())
                .commit()

            R.id.nav_listadeproductos -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListaproductosFragment())
                .commit()

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
