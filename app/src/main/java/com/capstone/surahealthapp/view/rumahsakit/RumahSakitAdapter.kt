package com.capstone.surahealthapp.view.rumahsakit

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.surahealthapp.data.response.RumahSakitResponse
import com.capstone.surahealthapp.databinding.ItemRowRumahSakitBinding
import com.capstone.surahealthapp.view.maps.MapsActivity

class RumahSakitAdapter : ListAdapter<RumahSakitResponse, RumahSakitAdapter.MyViewHolder>(DIFF_CALLBACK) {

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
        fun bind(item: RumahSakitResponse) {
            with(binding) {
                tvNamaRumahSakit.text = item.namaRumahSakit
                tvKamarKosong.text = item.jumlahBed.toString()
                tvKelas.text = item.kelas
                tvAntrian.text = item.tipe
                Glide.with(root.context)
                    .load(item.foto)
                    .fitCenter()
                    .into(ivPhotoRumahSakit)
                itemView.setOnClickListener{
                    val context = itemView.context
                    val intent = Intent(context, MapsActivity::class.java)
                    intent.putExtra(MapsActivity.EXTRA_KODE_RS, item)
                    context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RumahSakitResponse>() {
            override fun areItemsTheSame(oldItem: RumahSakitResponse, newItem: RumahSakitResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RumahSakitResponse, newItem: RumahSakitResponse): Boolean {
                return oldItem.kodeRumahSakit == newItem.kodeRumahSakit
            }
        }
    }
}