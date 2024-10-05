package com.tw.roomdbdemo.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tw.roomdbdemo.databinding.StateRowBinding
import com.tw.roomdbdemo.models.UserTable


class UsersAdapter : RecyclerView.Adapter<UsersAdapter.CustomVH>() {

    lateinit var mListener: OnItemClickListener
    var todosList = mutableListOf<UserTable?>()
    private var activity: Context? = null

    interface OnItemClickListener{
        fun onDataClick(model: UserTable, pos:Int, type: Int)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAdapterData(auditList: MutableList<UserTable>, activity: Context?, mListener: OnItemClickListener) {
        this.todosList = auditList.toMutableList()
        this.activity = activity
        this.mListener=mListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StateRowBinding.inflate(inflater, parent, false)
        return CustomVH(binding)
    }

    override fun onBindViewHolder(holder: CustomVH, position: Int) {
        val data = todosList[holder.adapterPosition]

        holder.binding.tvName.text = data!!.name
        holder.binding.tvPhone.text = data.phone
        holder.binding.tvEmail.text = data.email

        holder.binding.btnUpdate.setOnClickListener {
            val dataModel : UserTable? = todosList[holder.adapterPosition]
            mListener.onDataClick(dataModel!!, holder.adapterPosition, 1)
        }
    }

    override fun getItemCount(): Int {
        return todosList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class CustomVH(val binding: StateRowBinding) : RecyclerView.ViewHolder(binding.root) {}

}


