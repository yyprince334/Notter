package com.example.notter.fragment

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notter.R
import com.example.notter.data.models.NotterData
import com.example.notter.data.models.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(notterData: List<NotterData>){
        emptyDatabase.value = notterData.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }
            }
        }
    }

    fun verifyDataFromUser(title: String, description: String): Boolean {

        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description))
            false
        else !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> {
                Priority.High
            }
            "Medium Priority" -> {
                Priority.Medium
            }
            "Low Priority" -> {
                Priority.Low
            }
            else -> Priority.Low
        }
    }
}