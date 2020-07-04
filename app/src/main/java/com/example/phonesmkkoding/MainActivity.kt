package com.example.phonesmkkoding

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phonesmkkoding.adapter.PhoneAdapter
import com.example.phonesmkkoding.model.PhoneModel
import com.example.phonesmkkoding.viewmodel.PhoneActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    lateinit var ref: DatabaseReference
    var dataPhone: MutableList<PhoneModel> = ArrayList()
    private var adapter: PhoneAdapter? = null
    private val viewModel by viewModels<PhoneActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.init(this)
        getData()
        viewModel.allPhone.observe(this, Observer { hospitals ->
            hospitals?.let { adapter?.setData(it) }
        })

        rv_phone.layoutManager = LinearLayoutManager(this)
        adapter = PhoneAdapter(this, dataPhone)
        rv_phone.adapter = adapter

        search_bar.isSubmitButtonEnabled = true
        search_bar.queryHint = "Pencarian Berdasarkan Nama..."
        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getQueryData(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getQueryData(newText)
                return true
            }
        })
    }

    private fun getQueryData(query: String?) {
        val isi: String = "%$query%"
        viewModel.search(isi).observe(this, object : Observer<List<PhoneModel>> {
            override fun onChanged(phones: List<PhoneModel>?) {
                if (phones != null) {
                    adapter?.setData(phones)
                } else {
                    return
                }
            }

        })
    }

    private fun getData() {
        Toast.makeText(this@MainActivity, "Mohon tunggu sebentar.....", Toast.LENGTH_SHORT).show()
        val getUserID: String = auth.currentUser?.uid.toString()
        ref = FirebaseDatabase.getInstance().getReference()
        ref.child("Phone").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Database Error... \n" + p0.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataPhone = ArrayList()
                for (snapshot in dataSnapshot.children) {

                    val phone = snapshot.getValue(PhoneModel::class.java)
                    phone?.key = (snapshot.key!!)
                    dataPhone.add(phone!!)
                }
//                adapter?.setData(dataPhone)
                viewModel.insertAll(dataPhone)
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
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}