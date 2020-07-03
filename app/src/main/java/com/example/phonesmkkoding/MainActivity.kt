package com.example.phonesmkkoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phonesmkkoding.adapter.PhoneAdapter
import com.example.phonesmkkoding.model.PhoneModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    lateinit var ref : DatabaseReference
    var dataPhone: MutableList<PhoneModel> = ArrayList()
    private var adapter: PhoneAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()

        rv_phone.layoutManager = LinearLayoutManager(this)
        adapter = PhoneAdapter(this, dataPhone)
        rv_phone.adapter = adapter

    }

    private fun getData(){
        Toast.makeText(this@MainActivity, "Mohon tunggu sebentar.....", Toast.LENGTH_SHORT).show()
        val getUserID: String = auth.currentUser?.uid.toString()
        ref  = FirebaseDatabase.getInstance().getReference()
        ref.child("Phone").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MainActivity, "Database Error... \n" +p0.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataPhone = ArrayList()
                for (snapshot in dataSnapshot.children) {

                    val rs = snapshot.getValue(PhoneModel::class.java)
                    rs?.key = (snapshot.key!!)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            R.id.menu_logout -> {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}