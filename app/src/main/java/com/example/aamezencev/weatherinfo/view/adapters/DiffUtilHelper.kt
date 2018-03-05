package com.example.aamezencev.weatherinfo.view.adapters

import android.support.v7.util.DiffUtil

/**
 * Created by aa.mezencev on 05.03.2018.
 */
class DiffUtilHelper<T>(val oldList: List<T>,
                        val newList: List<T>,
                        val predicate: (T, T) -> Boolean) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] === newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            predicate(oldList[oldItemPosition], newList[newItemPosition])
}