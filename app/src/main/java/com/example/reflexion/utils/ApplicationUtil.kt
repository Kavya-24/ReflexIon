package com.example.reflexion.utils

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.FirebaseApp

class ApplicationUtil : Application() {


    companion object {

        lateinit var instance: ApplicationUtil


        fun getApplication(): ApplicationUtil {
            return instance
        }

        fun getContext(): Context {
            return instance.applicationContext
        }
    }


    override fun onCreate() {
        super.onCreate()

        //Initialize Firebase ctx
        FirebaseApp.initializeApp(this)


        //For getting the application context
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

}