package com.azzaqihaq.suitmediainterntest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class FirstScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // To turn off dark mode in activity
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        btn_check_palindrome.setOnClickListener {
            if (edt_palindrome.text.toString().isEmpty()){
                Toast.makeText(applicationContext, "Please enter the sentence first!", Toast.LENGTH_SHORT).show()
            }else{
                val inputPalindrome = edt_palindrome.text.toString()
                val isPalindrome = isPalindrome(inputPalindrome)
                Toast.makeText(applicationContext, "$isPalindrome", Toast.LENGTH_SHORT).show()
            }
        }

        btn_next.setOnClickListener {
            if (edt_name.text.toString().isEmpty()){
                Toast.makeText(applicationContext, "Please enter the name first!", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, SecondScreenActivity::class.java)
                intent.putExtra("name", edt_name.text.toString())
                intent.putExtra("username", "")
                startActivity(intent)
            }
        }
    }

    fun isPalindrome(input: String): String {
        val cleanInput = input.replace("\\W".toRegex(), "").toLowerCase()
        if (cleanInput == cleanInput.reversed() == true) return "Is Palindrome" else return "Not Palindrome"
    }
}