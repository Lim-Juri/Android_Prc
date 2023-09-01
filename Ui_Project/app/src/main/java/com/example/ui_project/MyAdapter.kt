package com.example.ui_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_project.databinding.ItemBinding
import java.text.DecimalFormat


class MyAdapter(val items: MutableList<MyItem>) : RecyclerView.Adapter<MyAdapter.Holder>() {

    //interface = 함수를 미리 정의해 놓음
    // 실제 onClick은 MainActivity에 있음
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    interface ItemLongClick {
        fun onLongClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    var itemLongClick: ItemLongClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: MyAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        holder.itemView.setOnLongClickListener() OnLongClickListener@{
            itemLongClick?.onLongClick(it, position)
            return@OnLongClickListener true
        }

        holder.iconImageView.setImageResource(items[position].aIcon)
        holder.name.text = items[position].aName
        holder.price.text = makeWon(items[position].aPrice)
        holder.address.text = items[position].aAddress
        holder.like.text = items[position].aLike.toString()
        holder.chat.text = items[position].aChat.toString()

        if (items[position].isLike)
            holder.heart.setImageResource(R.drawable.redheart)
        else
            holder.heart.setImageResource(R.drawable.heart)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun makeWon(aPrice: Int): String {
        val won = DecimalFormat("#,###원")
        var change = won.format(aPrice)
        return change.toString()
    }

    inner class Holder(val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val iconImageView = binding.icon
        val name = binding.itemTvName
        val price = binding.itemTvPrice
        val address = binding.itemTvAddress
        val like = binding.itemTvHeart
        val heart = binding.heart
        val chat = binding.itemTvChat
    }
}