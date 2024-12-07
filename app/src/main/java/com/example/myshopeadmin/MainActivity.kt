package com.example.myshopeadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myshopeadmin.AllFragments.HomeFragment
import com.example.myshopeadmin.AllFragments.UploadProductsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var buttonNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        buttonNavigationView = findViewById(R.id.bottomNavigation) as BottomNavigationView
        loadFragment(HomeFragment())
        buttonNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_wishlist -> {

                    return@setOnItemSelectedListener true
                }

                R.id.menu_card -> {
                    loadFragment(UploadProductsFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_search -> {

                    return@setOnItemSelectedListener true
                }
                R.id.menu_settings -> {

                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }


}