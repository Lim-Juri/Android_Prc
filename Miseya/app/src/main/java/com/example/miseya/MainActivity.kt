package com.example.miseya

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.miseya.databinding.ActivityMainBinding
import com.example.miseya.retrofit.NetWorkClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var item = mutableListOf<DustItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.spinnerViewDosi.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            communicateNetWork(setUpDustParameter(text))
        }

        binding.spinnerViewGo.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->

//            Log.d("miseya", "selectedItem: spinnerViewGo selected> $text")
            val selectedItem = item.filter { f -> f.stationName == text }
//            Log.d("miseya", "selectedItem: sidoName>" + selectedItem[0].sidoName)
//            Log.d("miseya", "selectedItem: pm10Value>" + selectedItem[0].pm10Value)

            binding.tvCityname.text = selectedItem[0].sidoName + " " + selectedItem[0].stationName
            binding.tvDate.text = selectedItem[0].dataTime
            binding.tvValue.text = selectedItem[0].pm10Value + " ㎍/㎥"

            when (getGrade(selectedItem[0].pm10Value)) {
                1 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#9ED2EC"))
                    binding.ivFace.setImageResource(R.drawable.image)
                    binding.tvGrade.text = "좋음"
                }

                2 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#D6A478"))
                    binding.ivFace.setImageResource(R.drawable.image2)
                    binding.tvGrade.text = "보통"
                }

                3 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#DF7766"))
                    binding.ivFace.setImageResource(R.drawable.image3)
                    binding.tvGrade.text = "나쁨"
                }

                4 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#BB3320"))
                    binding.ivFace.setImageResource(R.drawable.image4)
                    binding.tvGrade.text = "매우나쁨"
                }
            }
        }
    }

    //코루틴의 별도 스레드
    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch {

        val responseData = NetWorkClient.dustNetWork.getDust(param)

        Log.d("Parsing Dust ::", responseData.toString())

        item = responseData.response.dustBody.dustItem!!

        val go = ArrayList<String>()
        item.forEach {
            Log.d("add Item :", it.stationName)
            go.add(it.stationName)
        }

        runOnUiThread {
            binding.spinnerViewGo.setItems(go)
        }
    }


    private fun setUpDustParameter(dosi: String): HashMap<String, String> {
        val authKey =
            "AfC4c0vmGL4y+AaNSITAF999T/+OHNFKeSS40eOs4D/r7lNhwqn5VGLoB7hk5MA4RiZkeEjjp6CRcoUeyRq0nw=="

        return hashMapOf(
            //이름이 요청변수와 정확히 일치해야 한다.
            "serviceKey" to authKey,
            "returnType" to "json",
            "numOfRows" to "100",
            "pageNo" to "1",
            "sidoName" to dosi,
            "ver" to "1.0"
        )
    }

    fun getGrade(value: String): Int {
        val mValue = value.toInt()
        var grade = 1
        grade = if (mValue >= 0 && mValue <= 30) {
            1
        } else if (mValue >= 31 && mValue <= 80) {
            2
        } else if (mValue >= 81 && mValue <= 100) {
            3
        } else 4
        return grade
    }
}