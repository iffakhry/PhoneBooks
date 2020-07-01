package com.example.phonesmkkoding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        if (auth.currentUser != null) {
            val data = auth.currentUser
            if (data != null) {
                Glide.with(this).load(data.photoUrl).into(img_profil)
                name_profil.text = data.displayName
                user_id.text = data.uid
                email_profile.text = data.email
                no_phone.text = data.phoneNumber
            }
        } else {
            finish()
        }

        sign_out.setOnClickListener {
            auth.signOut()
            finish()
        }
    }
}