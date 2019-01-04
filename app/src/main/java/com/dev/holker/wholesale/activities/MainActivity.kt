package com.dev.holker.wholesale.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.fragments.ChatFragment
import com.dev.holker.wholesale.fragments.OrdersFragment
import com.dev.holker.wholesale.fragments.ProfileFragment
import com.dev.holker.wholesale.fragments.SearchFragment
import com.parse.ParseUser


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

    //intent to login screen
    private fun goToLogin() {
        val i = Intent(applicationContext, Login::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    //check if user logged
    private fun checkUser() {
        if (ParseUser.getCurrentSessionToken() == null) {
            goToLogin()
        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment): Boolean {
        val fm = supportFragmentManager
        fm.beginTransaction().replace(R.id.fragmemt_container, fragment).commit()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkUser()

        navigation_bottom.setOnNavigationItemSelectedListener(this)
        loadFragment(OrdersFragment())
    }
}
