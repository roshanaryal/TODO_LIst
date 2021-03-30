package com.aarappstudios.todofromskillshare.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aarappstudios.todofromskillshare.R
import com.aarappstudios.todofromskillshare.data.models.Priority
import com.aarappstudios.todofromskillshare.data.models.TodoData

class SharedViewModal(application: Application):AndroidViewModel(application) {


    val emptyDatabase:MutableLiveData<Boolean> = MutableLiveData(false)
    fun checkDataIfempty(tododata:List<TodoData>)
    {
        emptyDatabase.value=tododata.isEmpty()
    }
    val listner:AdapterView.OnItemSelectedListener=object:AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position)
            {
                0->{(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                    R.color.red))}
                1->{(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                    R.color.yellow))}
                2->{(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                    R.color.green))}
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }

    fun getPriority(priority: String): Priority {
        return when (priority) {
            "High" -> Priority.HIGH
            "Medium" -> Priority.MEDIUM
            "Low" -> Priority.LOW
            else -> Priority.MEDIUM
        }

    }

    fun verifyData(title: String, description: String): Boolean {

        return title.isNotEmpty()

    }



}