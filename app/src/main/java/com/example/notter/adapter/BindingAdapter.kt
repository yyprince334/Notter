package com.example.notter.adapter

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.notter.R
import com.example.notter.data.models.NotterData
import com.example.notter.data.models.Priority
import com.example.notter.fragment.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapter {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
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

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                Priority.High -> {
                    view.setSelection(0)
                }
                Priority.Medium -> {
                    view.setSelection(1)
                }
                Priority.Low -> {
                    view.setSelection(2)
                }

            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority) {
            when (priority) {
                Priority.High -> {
                    cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red))
                }
                Priority.Medium -> {
                    cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))
                }
                Priority.Low -> {
                    cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
                }
            }
        }
        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view:ConstraintLayout,currentItem:NotterData) {
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }

    }

}