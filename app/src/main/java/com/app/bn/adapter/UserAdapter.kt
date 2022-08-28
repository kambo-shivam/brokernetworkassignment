package com.app.bn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.bn.R
import com.app.bn.data.remote.BnResponse
import com.bumptech.glide.Glide
import com.app.bn.data.remote.User
import kotlinx.android.synthetic.main.row_list.view.*
import javax.inject.Inject

class UserAdapter @Inject constructor() :
    PagingDataAdapter<BnResponse, UserAdapter.UserViewHolder>(UserComparator) {

    object UserComparator : DiffUtil.ItemCallback<BnResponse>() {
        override fun areItemsTheSame(oldItem: BnResponse, newItem: BnResponse) =
            oldItem.cards == newItem.cards

        override fun areContentsTheSame(oldItem: BnResponse, newItem: BnResponse) =
            oldItem == newItem
    }

    inner class UserViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: BnResponse) {
            //itemView.tvName.text = item.name
            //Glide.with(itemView.context).load(item.image).into(itemView.ivAvatar)
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_list, parent, false)
        return UserViewHolder(view)
    }
}