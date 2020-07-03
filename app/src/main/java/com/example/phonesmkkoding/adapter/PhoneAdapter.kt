package com.example.phonesmkkoding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.phonesmkkoding.R
import com.example.phonesmkkoding.model.PhoneModel
import com.google.firebase.database.DatabaseReference
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.phone_item.view.*

class PhoneAdapter(private val context: Context, private var list: List<PhoneModel>): RecyclerView.Adapter<PhoneAdapter.ViewHolder>(){

    lateinit var ref: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.phone_item, parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))
    }

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: PhoneModel){
            containerView.nama.text = item.nama
            containerView.nomor.text = item.no_telp
        }
    }

    fun setData(list: List<PhoneModel>){
        this.list = list
        notifyDataSetChanged()
    }

}