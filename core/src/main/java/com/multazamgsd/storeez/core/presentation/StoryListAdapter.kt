package com.multazamgsd.storeez.core.presentation

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.multazamgsd.storeez.core.databinding.ItemStoryBinding
import com.multazamgsd.storeez.core.domain.model.Story
import com.multazamgsd.storeez.core.utils.GeneralHelper.toDateFormat

class StoryListAdapter : ListAdapter<Story, StoryListAdapter.ItemViewHolder>(diffCallback) {

    private lateinit var itemCallbacks: ItemCallbacks

    inner class ItemViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            binding.textAuthor.text = item.name
            binding.textDate.text = item.createdAt.toDateFormat()
            Glide.with(binding.root)
                .load(item.photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                        binding.loadingState.visibility = View.GONE
                        binding.imageStory.setBackgroundColor(ContextCompat.getColor(itemView.context, com.multazamgsd.storeez.core.R.color.D500))
                        return false
                    }

                    override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                        binding.loadingState.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.imageStory)

            binding.buttonFavorite.setImageDrawable(
                if (item.isFavorite) {
                    ContextCompat.getDrawable(itemView.context as Activity, com.multazamgsd.storeez.core.R.drawable.ic_favorite_filled)
                } else {
                    ContextCompat.getDrawable(itemView.context as Activity, com.multazamgsd.storeez.core.R.drawable.ic_favorite_border_grey)
                }
            )

            binding.root.setOnClickListener {
                itemCallbacks.onClick(item, adapterPosition)
            }

            binding.buttonFavorite.setOnClickListener {
                itemCallbacks.onFavoriteToggle(item, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun setOnItemCallback(itemCallbacks: ItemCallbacks) {
        this.itemCallbacks = itemCallbacks
    }

    interface ItemCallbacks {
        fun onClick(story: Story, position: Int)
        fun onFavoriteToggle(story: Story, position: Int)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}