package com.capstone.surahealthapp.view.rumahsakit

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.surahealthapp.MapsActivity
import com.capstone.surahealthapp.data.model.RumahSakit
import com.capstone.surahealthapp.databinding.ItemRowRumahSakitBinding

class RumahSakitAdapter : ListAdapter<RumahSakit, RumahSakitAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowRumahSakitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemRowRumahSakitBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RumahSakit) {
            with(binding) {
                tvNamaRumahSakit.text = item.nama_rm
                tvKamarKosong.text = item.kamar_kosong.toString()
                tvKelas.text = item.kelas
                Glide.with(root.context)
                    .load(item.rm_photoUrl)
                    .fitCenter()
                    .into(ivPhotoRumahSakit)
                itemView.setOnClickListener{
                    val context = itemView.context
                    val intent = Intent(context, MapsActivity::class.java)
                    intent.putExtra("item_id", item.id)
                    context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RumahSakit>() {
            override fun areItemsTheSame(oldItem: RumahSakit, newItem: RumahSakit): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RumahSakit, newItem: RumahSakit): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}