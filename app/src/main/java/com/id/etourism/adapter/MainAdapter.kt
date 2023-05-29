package com.id.etourism.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.id.etourism.data.network.model.Wisata
import com.id.etourism.databinding.ItemRowBinding

class MainAdapter(private val wisata: List<Wisata>) : RecyclerView.Adapter<MainAdapter.WisataViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class WisataViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wisata: Wisata) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(wisata)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(wisata.image)
                    .into(imgDestination)
                tvVillageName.text = wisata.name
                tvDescription.text = wisata.deskripsi
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        holder.bind(wisata[position])
    }

    override fun getItemCount(): Int = wisata.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Wisata)
    }
}