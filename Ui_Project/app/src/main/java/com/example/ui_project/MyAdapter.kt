package com.example.ui_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_project.databinding.ItemBinding
import java.text.DecimalFormat


class MyAdapter(val items: MutableList<MyItem>) : RecyclerView.Adapter<MyAdapter.Holder>() {
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.Holder {
        val binding =
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: MyAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        holder.iconImageView.setImageResource(items[position].aIcon)
        holder.name.text = items[position].aName
        holder.note.text = items[position].aNote
        holder.sale.text = items[position].aSale
        holder.price.text = makeWon(items[position].aPrice)
        holder.address.text = items[position].aAddress
        holder.like.text = items[position].aLike
        holder.chat.text = items[position].aChat

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun makeWon(aPrice:Int) :String {
        val text_won = DecimalFormat("#,###원")
        var str_change = text_won.format(aPrice)
        return str_change.toString()
    }

    inner class Holder(val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val iconImageView = binding.icon
        val name = binding.textItem1
        val note = binding.textItem2
        val sale = binding.textItem3
        val price = binding.textItem4
        val address = binding.textItem5
        val like = binding.textItem6
        val chat = binding.textItem7
    }
}