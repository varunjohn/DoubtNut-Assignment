package com.doubtnut.news.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface AdapterClickListener<T> {

    fun onClick(view : View, model: T, viewHolder: RecyclerView.ViewHolder)

}