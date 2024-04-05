package com.example.my_to_do_app.ui.addTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.my_to_do_app.databinding.FragmentAddTaskBinding
import com.example.my_to_do_app.database.model.MyDataBase
import com.example.my_to_do_app.database.model.Task
import com.example.my_to_do_app.ui.formatDate
import com.example.my_to_do_app.ui.formatTime
import com.example.my_to_do_app.ui.getDateOnly
import com.example.my_to_do_app.ui.getTimeOnly
import com.example.my_to_do_app.ui.showDialog

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskBottomSheet : BottomSheetDialogFragment() {
    lateinit var  binding:FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        binding.addTaskBtn.setOnClickListener{
            addTask()
        }
    }

    private fun setUpViews() {
        binding.selectDateTil.setOnClickListener{
            showDatePicker()
        }
        binding.selectTimeTil.setOnClickListener{
            showTimePicker()
        }
        binding.addTaskBtn.setOnClickListener{
            addTask()
        }
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

    val calendar = Calendar.getInstance()
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

    private fun isValidTaskInput(): Boolean {
        var isValid = true
        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        if (title.isBlank()){
            binding.titleTil.error = "Please enter task Title."
            isValid = false
        }
        else{
            binding.titleTil.error = null
        }
        if (description.isBlank()){
            binding.descriptionTil.error = "Please enter task Description."
            isValid = false
        }
        else{
            binding.descriptionTil.error = null
        }
        if (binding.selectTimeTv.text.isBlank()){
            binding.selectTimeTil.error = "Please select task Time"
            isValid = false
        }
        if (binding.selectDateTv.text.isBlank()){
            binding.selectDateTil.error = "Please select task Date"
            isValid = false
        }
        return isValid
    }

    private fun addTask() {
        if (!isValidTaskInput()) {
            return
        }
        MyDataBase.getInstance(requireContext().applicationContext)
            .getTaskDao()
            .insertTask(Task(
                title = binding.title.text.toString(),
                content = binding.description.text.toString(),
                date = calendar.getDateOnly(),
                time = calendar.getTimeOnly()
            ))
        showDialog("Task Inserted Successfully",
            posActionName = "ok",
            posActionCallBack = {
                dismiss()
                onTaskAddedListener?.onTaskAdded()
            },
            isCancelable = false
        )
    }

    var onTaskAddedListener:OnTaskAddedListener? = null
    fun interface OnTaskAddedListener{
        fun onTaskAdded()
    }
}
