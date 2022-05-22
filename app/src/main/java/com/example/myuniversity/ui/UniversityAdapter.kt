package com.example.myuniversity.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myuniversity.R
import com.example.myuniversity.local.room.entity.UnivEntity
import com.example.myuniversity.databinding.ItemUniversityBinding

class UniversityAdapter(private val onBookmarkClick: (UnivEntity) -> Unit) : androidx.recyclerview.widget.ListAdapter<UnivEntity, UniversityAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUniversityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val univ = getItem(position)
        holder.bind(univ)

        val ivBookmark = holder.binding.ivBookmark
        if (univ.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmarked_white))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_white))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(univ)
        }
    }


    class MyViewHolder(val binding: ItemUniversityBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(univ: UnivEntity) {
            binding.tvItemUniversity.text = univ.title
            binding.tvItemDomain.text = univ.url
            Glide.with(itemView.context)
                .load(univ.urlToImage)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imgPoster)
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = (Uri.parse(univ.url))
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UnivEntity> =
            object : DiffUtil.ItemCallback<UnivEntity>() {
                override fun areItemsTheSame(oldItem: UnivEntity, newItem: UnivEntity): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: UnivEntity, newItem: UnivEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }

}