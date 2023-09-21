package com.example.recyclerview

import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataList = mutableListOf(
            IconItem.MyTitle(1),
            IconItem.MyItem(R.drawable.astronaut, "Black", 1),
            IconItem.MyItem(R.drawable.beachboy, "red", 1),
            IconItem.MyItem(R.drawable.beer, "orange", 1),
            IconItem.MyItem(R.drawable.board, "green", 1),
            IconItem.MyItem(R.drawable.books, "blue", 1),
            IconItem.MyItem(R.drawable.bulb, "pink", 1),
            IconItem.MyTitle(2),
            IconItem.MyItem(R.drawable.chalk, "yellow", 2),
            IconItem.MyItem(R.drawable.cheer_up, "brown", 2),
            IconItem.MyItem(R.drawable.coding_dream, "white", 2),
            IconItem.MyItem(R.drawable.coinsheid, "purple", 2),
            IconItem.MyTitle(3),
            IconItem.MyItem(R.drawable.cornhead, "aqua", 3),
            IconItem.MyItem(R.drawable.desk, "gray", 3),
            IconItem.MyTitle(4),
            IconItem.MyItem(R.drawable.dj, "sky", 4),
            IconItem.MyItem(R.drawable.fall, "mint", 4),
            IconItem.MyItem(R.drawable.stabbing, "navy", 4),
            IconItem.MyItem(R.drawable.theif, "silver", 4),
        )

        val adapter = MyAdapter(dataList)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        binding.recyclerview.addItemDecoration(StickyHeaderDecoration())
        binding.recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        adapter.itemClick =
            object : MyAdapter.ItemClick {
                override fun onClick(view: View, position: Int) {
                    val name: String = (dataList[position] as IconItem.MyItem).Name
                    Toast.makeText(this@MainActivity, "$name 선택!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}