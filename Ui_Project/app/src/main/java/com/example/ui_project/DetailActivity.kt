package com.example.ui_project

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import com.example.ui_project.databinding.ActivityDetailBinding
import com.example.ui_project.databinding.ItemBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var isLike = false

    private val item: MyItem? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //객체 받아오기
            intent.getParcelableExtra("item_index", MyItem::class.java)
        } else {
            intent.getParcelableExtra<MyItem>("item_object")
        }
    }

    private val itemPosition: Int by lazy {
        intent.getIntExtra("item_index", 0)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showtoast("상세페이지로 이동합니다.")

        Log.d("hi", "itemPosition = $itemPosition")

        binding.mainImage.setImageDrawable(item?.let {
            ResourcesCompat.getDrawable(
                resources,
                it.aIcon,
                null
            )
        })

        binding.dtTvTitle.text = item?.aName
        binding.dtTvNote.text = item?.aNote
        binding.dtTvSale.text = item?.aSale
        binding.dtTvPrice.text = DecimalFormat("#,###").format(item?.aPrice) + "원"
        binding.dtTvAddress.text = item?.aAddress

        isLike = item?.isLike == true

        binding.dtHeart.setImageResource(if (isLike) {R.drawable.redheart} else {R.drawable.heart})

        binding.dtHeart.setOnClickListener {
            if (!isLike) {
                binding.dtHeart.setImageResource(R.drawable.redheart)
                Snackbar.make(binding.constraint, "관심 목록에 추가 되었습니다.", Snackbar.LENGTH_SHORT).show()
                isLike = true
            } else {
                binding.dtHeart.setImageResource(R.drawable.heart)
                isLike = false
            }
        }

        binding.back.setOnClickListener {
            exit()
        }
    }

    override fun onBackPressed() {
        exit()
    }

    fun exit() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("item_index", itemPosition)
            putExtra("isLike", isLike)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}