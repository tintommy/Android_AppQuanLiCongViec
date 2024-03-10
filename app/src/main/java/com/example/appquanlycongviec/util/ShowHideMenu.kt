package com.example.appquanlycongviec.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.appquanlycongviec.MainActivity
import com.example.appquanlycongviec.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShowHideMenu {

    fun Fragment.hideBottomNavigation() {
        val bottomNavigation =
            (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomMenu)

        bottomNavigation.visibility = View.GONE

    }

    fun Fragment.showBottomNavigation() {
        val bottomNavigation =
            (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomMenu)

        bottomNavigation.visibility = View.VISIBLE

    }
}