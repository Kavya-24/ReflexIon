package com.example.reflexion.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.reflexion.MainActivity
import com.example.reflexion.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {


    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        FirebaseApp.initializeApp(this)

        handler = Handler()
        handler.postDelayed({


            if (FirebaseAuth.getInstance().currentUser == null) {
                //When we are not signed in
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i);

            } else {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i);

            }

            finish()

        }, 1000)


    }


}

