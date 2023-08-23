package com.example.dialog

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.dialog.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 1. 기본 다이얼로그
        binding.btn1Alert.setOnClickListener {
            var builder = AlertDialog.Builder(this)
//            기본 다이얼로그 타이틀
            builder.setTitle("Welcome")
//            기본 다이얼로그 메세지
            builder.setMessage("환영합니다.")
            builder.setIcon(R.drawable.cw_main)

            // 버튼 클릭시에 무슨 작업을 할 것인가!
            val listener = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    when (p1) {
                        DialogInterface.BUTTON_POSITIVE ->
                            binding.tvTitle.text = "BUTTON_POSITIVE"

                        DialogInterface.BUTTON_NEUTRAL ->
                            binding.tvTitle.text = "BUTTON_NEUTRAL"

                        DialogInterface.BUTTON_NEGATIVE ->
                            binding.tvTitle.text = "BUTTON_NEGATIVE"
                    }
                }
            }

            builder.setPositiveButton("들어가기", listener)
            builder.setNegativeButton("나가기", listener)
            builder.setNeutralButton("돌아가기", listener)

            builder.show()
        }


//        2. 커스텀 다이얼로그
        binding.btn2Custom.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("이름과 나이를 입력해 주세요.")
            builder.setIcon(R.drawable.cw_main)

            val v1 = layoutInflater.inflate(R.layout.dialog, null)
            builder.setView(v1)

//            p0에 해당 AlertDialog가 들어온다.
            //            findViewById를 통해 view를 가져와서 사용
            val listener = DialogInterface.OnClickListener { p0, p1 ->
                val alert = p0 as AlertDialog
                val edit1: EditText? = alert.findViewById<EditText>(R.id.editText)
                val edit2: EditText? = alert.findViewById<EditText>(R.id.editText2)

                binding.tvTitle.text = "이름 : ${edit1?.text}"
                binding.tvTitle.append(" / 나이 : ${edit2?.text}")
            }

            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", null)

            builder.show()
        }


        // 3. 날짜 다이얼로그
        binding.btn3Date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i년 i2월 i3일
                binding.tvTitle.text = "${i}년 ${i2 + 1}월 ${i3}일"
            }

            var picker = DatePickerDialog(this, listener, year, month, day)
            picker.show()
        }


        // 4. 시간 다이얼로그
        binding.btn4Time.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)

            val listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                binding.tvTitle.text = "${i}시 ${i2}분"
            }

            val picker = TimePickerDialog(this, listener, hour, minute, false) // true하면 24시간 제
            picker.show()
        }


        // 5. 프로그레스 다이얼로그
        binding.btn5Porgress.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("다운로드 받는 중")
            builder.setIcon(R.drawable.cw_main)

            val v1 = layoutInflater.inflate(R.layout.progressbar, null)
            builder.setView(v1)

            builder.show()
        }
    }
}