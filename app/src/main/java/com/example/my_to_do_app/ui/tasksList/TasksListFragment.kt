package com.example.my_to_do_app.ui.tasksList

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.my_to_do_app.Constants
import com.example.my_to_do_app.R
import com.example.my_to_do_app.database.model.MyDataBase
import com.example.my_to_do_app.databinding.FragmentTasksBinding
import com.example.my_to_do_app.ui.EditTaskFragment
import com.example.my_to_do_app.ui.home.TasksAdapter
import com.prolificinteractive.materialcalendarview.CalendarDay


class TasksListFragment: Fragment() {

    lateinit var binding: FragmentTasksBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        adapter.setOnTaskClickListener{task ->
            if (activity==null) return@setOnTaskClickListener
            val fragment = EditTaskFragment()
            val bundle = Bundle()
            bundle.putParcelable(Constants.PASSED_TASK, task)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R
                    .id.fragment_container, fragment)
                .addToBackStack("")
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        retreiveTasksList()
    }

    val currentDate = Calendar.getInstance()
     fun retreiveTasksList() {
       val allTasks = MyDataBase.getInstance(requireContext().applicationContext)
            .getTaskDao()
            .getTasksByDate(currentDate.setDateOnly())
        adapter.changeData(allTasks)
    }

    val adapter = TasksAdapter()
    private fun setUpViews() {
        binding.rvTasks.adapter = adapter
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected){
                currentDate.set(date.year,date.month-1,date.day)
                retreiveTasksList()
            }
        }
        binding.calendarView.selectedDate = CalendarDay.today()
    }
}

private fun Calendar.setDateOnly() {

}

