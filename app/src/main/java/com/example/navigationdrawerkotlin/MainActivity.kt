package com.example.navigationdrawerkotlin
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener { private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, homeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { when (item.itemId) {
        R.id.nav_home -> supportFragmentManager.beginTransaction() .replace(R.id.fragment_container, homeFragment()).commit()
        R.id.nav_settings -> supportFragmentManager.beginTransaction() .replace(R.id.fragment_container, settingsFragment()).commit()
        R.id.nav_categorias -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CategoriasFragment()).commit()
        R.id.nav_logout-> supportFragmentManager.beginTransaction() .replace(R.id.fragment_container, LogoutFragment()).commit()
        R.id.nav_busqueda-> supportFragmentManager.beginTransaction() .replace(R.id.fragment_container, BuscarFragment()).commit()
        R.id.nav_quejas-> supportFragmentManager.beginTransaction() .replace(R.id.fragment_container, PqrsFragment()).commit()
        R.id.nav_productos-> supportFragmentManager.beginTransaction() .replace(R.id.fragment_container, MisproductosFragment()).commit()
        R.id.nav_cuenta-> supportFragmentManager.beginTransaction() .replace(R.id.fragment_container, CuentaFragment()).commit()
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