package com.example.homework_6_3

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.homework_6_3.databinding.ItemHashtagBinding

@SuppressLint("NotifyDataSetChanged")
class HashtagAdapter(
    val onClick: (hint: String) -> Unit,
    private val list: ArrayList<String> = arrayListOf()
) : Adapter<HashtagAdapter.HashtagHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HashtagHolder {
        return HashtagHolder(
            ItemHashtagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HashtagHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class HashtagHolder(private val binding: ItemHashtagBinding) : ViewHolder(binding.root) {
        fun bind(hashtag: String) {
            binding.textView.text = hashtag
        }
    }

    fun addHashtag(hashtag: ArrayList<String>) {
        list.clear()
        list.addAll(hashtag)
        notifyDataSetChanged()
    }
}