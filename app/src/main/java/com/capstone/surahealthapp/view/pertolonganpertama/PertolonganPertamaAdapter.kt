package com.capstone.surahealthapp.view.pertolonganpertama

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.surahealthapp.data.lokal.PertolonganPertamaEntity
import com.capstone.surahealthapp.databinding.ItemPertolonganPertamaRowBinding
import com.capstone.surahealthapp.view.detailpp.DetailPertolonganPertamaActivity


class PertolonganPertamaAdapter : ListAdapter<PertolonganPertamaEntity, PertolonganPertamaAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPertolonganPertamaRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemPertolonganPertamaRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PertolonganPertamaEntity){
            with(binding){
                tvPenyakit.text = item.pertolongan_pertama

                itemView.setOnClickListener{
                    val context = itemView.context
                    val intent = Intent(context, DetailPertolonganPertamaActivity::class.java)
                    intent.putExtra("item_id", item.id)
                    context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PertolonganPertamaEntity>() {
            override fun areItemsTheSame(oldItem: PertolonganPertamaEntity, newItem: PertolonganPertamaEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PertolonganPertamaEntity, newItem: PertolonganPertamaEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}