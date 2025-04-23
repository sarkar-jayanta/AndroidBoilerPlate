package com.boilerplate.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T, VB : ViewDataBinding, BVH : BaseAdapter.BaseViewHolder<T>>(
    private var items: ArrayList<T>,
    private val layoutId: Int,
    private val viewHolderFactory: (VB) -> BVH,
) : RecyclerView.Adapter<BVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VB>(inflater, layoutId, parent, false)
        return viewHolderFactory(binding)
    }

    override fun onBindViewHolder(holder: BVH, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: ArrayList<T>) {
        items = newList
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }
}