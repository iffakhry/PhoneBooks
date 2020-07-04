package com.example.phonesmkkoding

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getPermissionToReadUserContacts()

        progress.visibility = View.GONE
        login.setOnClickListener {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                    .setIsSmartLockEnabled(false)
                    .build(),
                RC_SIGN_IN
            )

            progress.visibility = View.VISIBLE
        }
        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser == null) {
        } else {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                progress.visibility = View.GONE
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val CALL_PERMISSIONS_REQUEST = 1
    
    @RequiresApi(Build.VERSION_CODES.M)
    fun getPermissionToReadUserContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.CALL_PHONE
                )
            )
            requestPermissions(
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PERMISSIONS_REQUEST
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == CALL_PERMISSIONS_REQUEST) {
            if (grantResults.size == 1 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show()
            } else {
                val showRationale =
                    shouldShowRequestPermissionRationale( Manifest.permission.CALL_PHONE )
                if (showRationale) {
                    // lakukan sesuatu disini
                } else {
                    Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}