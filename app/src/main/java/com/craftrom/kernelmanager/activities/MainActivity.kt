package com.craftrom.kernelmanager.activities

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.craftrom.kernelmanager.fragments.BottomNavigationDrawerFragment
import com.craftrom.kernelmanager.R
import com.craftrom.kernelmanager.fragments.IntroFragment
import com.craftrom.kernelmanager.fragments.ProfilesFragment
import com.craftrom.kernelmanager.fragments.SettingsFragment
import com.craftrom.kernelmanager.fragments.AboutFragment
import com.craftrom.kernelmanager.utils.FileUtils
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.widget.Toolbar

object Choice {
    var choice = 0
    var isMainRedrawn = 1
}

class MainActivity : AppCompatActivity() {
    lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.refresh)

        setSupportActionBar(bottom_app_bar)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.contentLayout, IntroFragment())
        ft.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }
        }
        return true
    }

    fun fetchChoice(oldChoice: Int) {
        if (oldChoice != Choice.choice) {
            Handler().postDelayed(Runnable {
                switchFragments(oldChoice)
            }, 300)
        }
    }

    private fun switchFragments(oldChoice: Int) {
        when (Choice.choice) {
            0 -> {
                val ft = supportFragmentManager.beginTransaction()
                ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                ft.replace(R.id.contentLayout, IntroFragment())
                ft.commit()
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_reload))
            }
            1 -> {
                val ft = supportFragmentManager.beginTransaction()
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                ft.replace(R.id.contentLayout, ProfilesFragment())
                ft.commit()
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_close))
            }
            2 -> {
                val ft = supportFragmentManager.beginTransaction()
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                ft.replace(R.id.contentLayout, SettingsFragment())
                ft.commit()
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_close))
            }
            3 -> {
                val ft = supportFragmentManager.beginTransaction()
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                ft.replace(R.id.contentLayout, AboutFragment())
                ft.commit()
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_close))
            }
        }
    }

    override fun onBackPressed() {
        if (Choice.choice != 0) {
            val old = Choice.choice
            Choice.choice = 0
            switchFragments(old)
        } else {
            super.onBackPressed()
        }
    }

}
** Called when the new exit button is clicked. */
   public void nav_exit(View view)
		{

        finish();
	}
