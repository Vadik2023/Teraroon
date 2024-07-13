package com.example.teraroon

import android.content.Context
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.teraroon.databinding.ActivityGameBinding
import com.example.teraroon.ui.home.HomeFragment

class GameActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityGameBinding
    private var login: String = ""
    private var pass: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarGame.toolbar)

        login = intent.getStringExtra("login")!!
        pass = intent.getStringExtra("pass")!!

        val bundle = Bundle().apply {
            putString("login", login)
            putString("pass", pass)
        }

        val homeFragment = HomeFragment().apply {
            arguments = bundle
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_game, homeFragment)
            .commit()

        binding.appBarGame.fab.setOnClickListener { view ->
            Snackbar.make(view, "Чё не понятно — спрашивай у разраба!", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_game)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.virus_game, R.id.molecule_game, R.id.add_new_brawler
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun whoUser(): User {
        return User(login, "", pass)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.game, menu)
        return true
    }

    fun getContext(): Context {
        return this
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_game)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}