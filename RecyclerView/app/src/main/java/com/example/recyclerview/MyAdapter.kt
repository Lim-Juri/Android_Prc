package com.example.recyclerview

import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recyclerview.databinding.ItemRecyclerviewBinding
import com.example.recyclerview.databinding.ItemTitleBinding

class MyAdapter(val Items: MutableList<IconItem>) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_TITLE = 100
        private const val VIEW_TYPE_ICON = 200
    }

    interface StickyHeader {
        val stickyId: Any
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (viewType) {
            VIEW_TYPE_TITLE -> {
                val binding =
                    ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TitlewViewHolder(binding)
            }

            else -> {
                val binding = ItemRecyclerviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                IconViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = Items[position]) {
            is IconItem.MyTitle -> {
                (holder as TitlewViewHolder).title.text = "${item.age}살"
            }

            is IconItem.MyItem -> {
                (holder as IconViewHolder).name.text = item.Name
                holder.age.text = item.Age.toString()
                holder.iconImageView.setImageResource(item.Icon)

                holder.itemView.setOnClickListener {
                    itemClick?.onClick(it, position)
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return Items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (Items[position]) {
            is IconItem.MyTitle -> VIEW_TYPE_TITLE
            is IconItem.MyItem -> VIEW_TYPE_ICON
        }
    }

    inner class TitlewViewHolder(binding: ItemTitleBinding) : RecyclerView.ViewHolder(binding.root),StickyHeader {
        val title = binding.tvAgetitle

        override val stickyId: Int
            get() = (Items[adapterPosition] as IconItem.MyTitle).age
    }

    inner class IconViewHolder(binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val iconImageView = binding.imageItem
        val name = binding.textItemName
        val age = binding.textItemAge
    }
}

