package com.example.my_to_do_app.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.my_to_do_app.Constants
import com.example.my_to_do_app.database.model.MyDataBase
import com.example.my_to_do_app.database.model.Task
import com.example.my_to_do_app.databinding.FragmentEditTaskBinding

import java.util.Calendar

class EditTaskFragment :Fragment() {
    private lateinit var binding : FragmentEditTaskBinding
    private lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task = getPassedTask()
        bindTask(task)
        binding.selectTimeTv.setOnClickListener { showTimePicker() }
        binding.selectDateTv.setOnClickListener { showDatePicker() }
        binding.saveTaskBtn.setOnClickListener { saveTask() }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getPassedTask(): Task {
        arguments.let {
            return arguments?.getParcelable(Constants.PASSED_TASK, Task::class.java) ?: Task()
        }
    }
    val calendar = Calendar.getInstance()
    private fun bindTask(task: Task) {
        binding.title.setText(task.title.toString())
        binding.description.setText(task.content.toString())
        binding.selectDateTv.text = calendar.getDateOnly().toString()
        binding.selectTimeTv.text = calendar.getTimeOnly().toString()
        binding.isDoneCheckBox.isChecked = task.isDone
    }

    private fun showTimePicker() {
        val timePicker = TimePickerDialog(requireContext(),
            { dialog, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY,hour)
                calendar.set(Calendar.MINUTE,minute)
                binding.selectTimeTv.text = calendar.formatTime()
                binding.selectTimeTil.error = null
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePicker.show()
    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(requireContext())
        datePicker.setOnDateSetListener { dialog, year, month, day ->
            calendar.set(Calendar.DAY_OF_MONTH,day)
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            binding.selectDateTv.text = calendar.formatDate()
            binding.selectDateTil.error = null
        }
        datePicker.show()
    }

    private fun saveTask(){
        val newTask = Task(
            task.id,
            binding.title.text.toString(),
            binding.description.text.toString(),
            binding.isDoneCheckBox.isChecked,
            calendar.time.time
        )
        MyDataBase.getInstance(requireContext()).getTaskDao().updateTask(newTask)
    }
}