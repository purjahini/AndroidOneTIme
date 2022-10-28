package com.didik.androidonetime.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.didik.androidonetime.R
import com.didik.androidonetime.model.mahasiswaModel


class AdapterMahasiswa(val context: Context, var listItemMahasiswa: List<mahasiswaModel.Data>) :
    RecyclerView.Adapter<AdapterMahasiswa.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(mahasiswa: mahasiswaModel.Data, context: Context) {
            Glide.with(context)
                .load(Uri.parse(mahasiswa.image))
                .placeholder(R.drawable.logo)
                .into(itemView.findViewById(R.id.IvMahasisiwa))
            itemView.findViewById<TextView>(R.id.TvEmailMahasiswa).setText(mahasiswa.email)
            itemView.findViewById<TextView>(R.id.TvVisiMahasiswa).setText(mahasiswa.visi)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMahasiswa.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_mahasiswa, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItemMahasiswa.get(position)
        if (item != null) {
            holder.onBind(item, context)
        }


    }

    override fun getItemCount(): Int = listItemMahasiswa!!.size
}