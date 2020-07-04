package com.example.phonesmkkoding.adapter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.phonesmkkoding.DetailActivity
import com.example.phonesmkkoding.R
import com.example.phonesmkkoding.model.PhoneModel
import com.google.firebase.database.DatabaseReference
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.phone_item.view.*

class PhoneAdapter(private val context: Context, private var list: List<PhoneModel>) :
    RecyclerView.Adapter<PhoneAdapter.ViewHolder>() {

    lateinit var ref: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.phone_item, parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))

        holder.itemView.setOnClickListener { view ->
            val action = arrayOf("Panggil", "Detail")
            val alert = AlertDialog.Builder(view.context)
            alert.setItems(action) { dialog, i ->
                when (i) {
                    0 -> {
                        val intent =
                            Intent(Intent.ACTION_CALL, Uri.parse("tel:${list[position].no_telp}"))
                        if (ActivityCompat.checkSelfPermission(
                                view.context,
                                Manifest.permission.CALL_PHONE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            Toast.makeText(
                                context, "Panggil",
                                Toast.LENGTH_SHORT
                            ).show()
                            context.startActivity(intent)
                        }else{
                            Toast.makeText(view.context, "Call permission granted", Toast.LENGTH_SHORT).show()
                        }
                    }
                    1 -> {
                        val bundle = Bundle()
                        bundle.putString("nama", list[position].nama)
                        bundle.putString("alamat", list[position].alamat)
                        bundle.putString("jenis", list[position].jenis)
                        bundle.putString("notelp", list[position].no_telp)
                        bundle.putString("latitude", list[position].latitude)
                        bundle.putString("longitude", list[position].longitude)
                        val intent =
                            Intent(view.context, DetailActivity::class.java)
                        intent.putExtras(bundle)
                        context.startActivity(intent)
                    }
                }
            }
            alert.create()
            alert.show()
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(item: PhoneModel) {
            containerView.nama.text = item.nama
            containerView.nomor.text = item.no_telp
        }
    }

    fun setData(list: List<PhoneModel>) {
        this.list = list
        notifyDataSetChanged()
    }

}