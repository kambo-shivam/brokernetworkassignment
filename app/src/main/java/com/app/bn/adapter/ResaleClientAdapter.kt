package com.app.bn.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.bn.R
import com.app.bn.data.remote.Card
import com.app.bn.data.remote.MainPost
import com.app.bn.databinding.RowListBinding
import com.bumptech.glide.Glide


/**
 * Created by Raj on 08, June, 2020
 */

class ResaleClientAdapter(var context: Activity, var list: ArrayList<Card>?) :
    RecyclerView.Adapter<ResaleClientAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list!![position]
        if (position > 0)
            holder.binding.relPfp.setBackgroundColor(context.getColor(R.color.blue))
        setDataOnView(holder, data)

    }


    private fun setDataOnView(holder: MyViewHolder, data: Card) {
        val itemData = data.data?.main_post
        holder.binding.title.text = itemData?.title
        holder.binding.tvInfo.text = itemData?.info
        holder.binding.tvSubInfo.text = getSubInfo(itemData)

        setViewPager(holder, data)

    }

    private fun getSubInfo(itemData: MainPost?): String? {
        var subInfo = ""
        for (i in 0..((itemData?.sub_info?.size?.minus(1)) ?: 0)) {
            val subInfoItem = itemData?.sub_info?.get(i)?.text.toString()

            if (i != 0) {
                subInfo = subInfo + " ${context.getString(R.string.text_bullet)} " + subInfoItem
            } else
                subInfo += subInfoItem
        }
        return subInfo
    }

    private fun setViewPager(holder: MyViewHolder, data: Card) {
        val childNotificationAdapter = RentalClientAdapter(context, data.data?.horizontal_cards)
        holder.binding.viewPager.adapter = childNotificationAdapter
        holder.binding.dot1.setViewPager(holder.binding.viewPager)
        holder.binding.viewPager.clipToPadding = false;
        holder.binding.viewPager.setPadding(48, 0, 48, 0);
        holder.binding.viewPager.pageMargin = 24;
    }

    /**
     * Responsibility- This Method will return Number of item present in List
     * **/
    override fun getItemCount(): Int {
        return list?.size!!
    }


    inner class MyViewHolder(var binding: RowListBinding) :
        RecyclerView.ViewHolder(binding.root)

}