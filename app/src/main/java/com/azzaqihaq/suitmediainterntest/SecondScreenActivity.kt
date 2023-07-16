package com.azzaqihaq.suitmediainterntest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_second_screen.*

class SecondScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        // To turn off dark mode in activity
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Setup the action bar
        val actionBar = supportActionBar
        actionBar!!.title = "Second Screen"
        actionBar.setDisplayHomeAsUpEnabled(true)

        // get all data intent
        val name = intent.getStringExtra("name").toString()
        val username = intent.getStringExtra("username").toString()

        // check the intent name, to display the data
        if(name != "") {
            tv_name.text = name
        } else {
            tv_name.text = "John Doe"
        }

        if(username != "") {
            tv_selected_username.text = username
        } else {
            tv_selected_username.text  = "Selected User Name"
        }

        btn_choose_user.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java).also {
                it.putExtra("name", tv_name.text.toString())
                startActivity(it)
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}