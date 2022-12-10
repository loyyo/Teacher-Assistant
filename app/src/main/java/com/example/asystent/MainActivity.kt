package com.example.asystent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView : NavigationView = findViewById(R.id.nav_view)

        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragment(ZajeciaFragment(), "Zajęcia")
        navView.setNavigationItemSelectedListener {
            it.isChecked = true
            when(it.itemId) {
                R.id.nav_zajecia -> replaceFragment(ZajeciaFragment(), it.title.toString())
                R.id.nav_uczniowie -> replaceFragment(UczniowieFragment(), it.title.toString())
                R.id.nav_dodaj_zajecia -> replaceFragment(DodajZajeciaFragment(), it.title.toString())
                R.id.nav_dodaj_ucznia -> replaceFragment(DodajUczniaFragment(), it.title.toString())
                R.id.nav_dodaj_ocene -> replaceFragment(DodajOceneFragment(), it.title.toString())
                R.id.nav_ustawienia -> replaceFragment(UstawieniaFragment(), it.title.toString())
                R.id.nav_przypisz_zajecia -> replaceFragment(ZajeciaUczniaFragment(), it.title.toString())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, title : String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item)
    }
}