package com.aarappstudios.todofromskillshare

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.aarappstudios.todofromskillshare.data.models.Priority
import com.aarappstudios.todofromskillshare.data.models.TodoData
import com.aarappstudios.todofromskillshare.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapter {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_createFragment)
                }
            }
        }


        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:populatePriority")
        @JvmStatic
        fun populatePriority(view: Spinner, priority: Priority) {
            view.setSelection(
                when (priority) {
                    Priority.HIGH -> 0
                    Priority.MEDIUM -> 1
                    Priority.LOW -> 2
                }
            )
        }

        @BindingAdapter("android:populatePriorityColor")
        @JvmStatic
        fun populatePriorityColor(view: CardView, priority: Priority) {
            when (priority) {
                Priority.HIGH -> {
                    view.setCardBackgroundColor(
                        ContextCompat.getColor(
                            view.context,
                            R.color.red
                        )
                    )
                }
                Priority.MEDIUM -> {
                    view.setCardBackgroundColor(
                        ContextCompat.getColor(
                            view.context,
                            R.color.yellow
                        )
                    )
                }
                Priority.LOW -> {
                    view.setCardBackgroundColor(
                        ContextCompat.getColor(
                            view.context,
                            R.color.green
                        )
                    )
                }
            }
        }


        @BindingAdapter("android:navigateToUpdateFragment")
        @JvmStatic
        fun navigateToUpdateFragment(view:ConstraintLayout,todoData: TodoData) {
            view.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(todoData)
            view.findNavController().navigate(action)
        }
        }

    }
}