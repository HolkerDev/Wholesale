package com.dev.holker.wholesale

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.navigation_orders -> {
                loadFragment(OrdersFragment())
                return true
            }
            R.id.navigation_search -> {
                loadFragment(SearchFragment())
                return true
            }
            R.id.navigation_chat -> {
                loadFragment(ChatFragment())
                return true
            }
            R.id.navigation_profile -> {
                loadFragment(ProfileFragment())
                return true
            }
        }
        return false
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragmemt_container, fragment).commit()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation_bottom.setOnNavigationItemSelectedListener(this)
        loadFragment(OrdersFragment())
    }
}
