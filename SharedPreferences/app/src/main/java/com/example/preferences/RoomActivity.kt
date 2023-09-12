package com.example.preferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.preferences.databinding.ActivityRoomBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRoomBinding.inflate(layoutInflater) }
    lateinit var myDao: MyDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myDao = MyDatabase.getDatabase(this).getMyDao()

        val allStudent = myDao.getAllStudents()
        allStudent.observe(this) {
            val str = StringBuilder().apply {
                for ((id, name) in it) {
                    append(id)
                    append("-")
                    append("name")
                    append("\n")
                }
            }.toString()
            binding.textListSt.text = str
        }

        binding.addSt.setOnClickListener {
            val id = binding.editStId.text.toString().toInt()
            val name = binding.editStName.text.toString()
            if (id>0&&name.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    myDao.insertStudent(Student(id,name))
                }
            }

            binding.editStId.text = null
            binding.editStName.text = null
        }

        binding.querySt.setOnClickListener {
            val name = binding.editStName.text.toString()
            CoroutineScope(Dispatchers.IO).launch {

                val results = myDao.getStudentByName(name)

                if (results.isNotEmpty()) {
                    val str = StringBuilder().apply {
                        results.forEach{
                            student -> append(student.id)
                            append("-")
                            append(student.name)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        binding.textQuerySt.text = str
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.textQuerySt.text = ""
                    }
                }
            }
        }
    }
}