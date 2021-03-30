package com.aarappstudios.todofromskillshare

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun hideKeyboard(activity: Activity) {
    val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentfocusview = activity.currentFocus
    currentfocusview?.let {
        inputManager.hideSoftInputFromWindow(
            currentfocusview.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner,observer:Observer<T>){
    observe(lifecycleOwner,object:Observer<T>{
        override fun onChanged(t: T) {
            observer.onChanged(t)
            removeObserver(this)
        }

    })
}