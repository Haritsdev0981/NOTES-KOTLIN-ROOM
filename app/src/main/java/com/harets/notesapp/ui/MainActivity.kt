package com.harets.notesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.harets.notesapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        supportActionBar?.elevation = 0f

        //untuk mengatur navigasi host itu si main
//        val navHost =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment?
//
//
//        if (navHost != null){
//            val appBarConfiguration = AppBarConfiguration(navHost.navController.graph)
//            setupActionBarWithNavController(navHost.navController, appBarConfiguration)
//        }

    }

    //untuk mengatur menu pada toolbar
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_home, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}