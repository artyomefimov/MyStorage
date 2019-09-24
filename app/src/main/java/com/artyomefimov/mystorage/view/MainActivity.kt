package com.artyomefimov.mystorage.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.artyomefimov.mystorage.R
import com.artyomefimov.mystorage.view.list.ProductListFragment

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.addOnBackStackChangedListener(this)

        var listFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (listFragment == null) {
            listFragment = ProductListFragment.newInstance()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, listFragment)
                commit()
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onBackStackChanged() {
        isShouldDisplayHomeUp()
    }

    private fun isShouldDisplayHomeUp() {
        val canGoBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(canGoBack)
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return false
    }
}
