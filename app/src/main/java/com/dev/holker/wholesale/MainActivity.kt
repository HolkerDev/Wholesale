package com.dev.holker.wholesale

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import android.R.anim.slide_out_right
import android.R.anim.slide_in_left
import android.content.Intent
import android.support.v4.app.FragmentManager
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

    private fun loadFragment(fragment: Fragment): Boolean {
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
