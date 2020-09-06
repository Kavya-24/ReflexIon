package com.example.reflexion.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.reflexion.MainActivity
import com.example.reflexion.R
import com.example.reflexion.utils.FirestoreUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import org.jetbrains.anko.*


class LoginActivity : AppCompatActivity() {


    private val RC_code = 1

    //private val signInProviders= listOf(Auth)
    //email authentication
    private val signInProviders =
        listOf(
            AuthUI.IdpConfig.EmailBuilder()
                .setAllowNewAccounts(true)
                .setRequireName(true)
                .build()
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val b = findViewById<Button>(R.id.account_sign_in)
        b.setOnClickListener {
            val i = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(signInProviders).build()

            startActivityForResult(i, RC_code)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_code) {

            //When the access token is correct and we are able to signin
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {

                val progressDialog = indeterminateProgressDialog("Setting up your account")

                FirestoreUtil.initCurrentUserIfFirstTime {
                    startActivity(intentFor<MainActivity>().newTask().clearTask())
                    progressDialog.dismiss()

                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response == null) {

                    return
                }

                when (response.error?.errorCode) {
                    ErrorCodes.NO_NETWORK -> longToast("No network connection, please try again later")
                    ErrorCodes.DEVELOPER_ERROR -> longToast("Developer Error")
                    ErrorCodes.UNKNOWN_ERROR -> longToast("Unknown error occurred")
                    ErrorCodes.PROVIDER_ERROR -> longToast("Provider Error")

                }
            }


        }

    }


}
