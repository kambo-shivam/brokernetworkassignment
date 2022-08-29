package com.app.bn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.bn.R
import com.app.bn.data.remote.HorizontalCard
import com.app.bn.databinding.ChildRowListBinding


class RentalClientAdapter(
    private val context: Context,
    private var horizontalCards: List<HorizontalCard>?
) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null
    override fun getCount(): Int {
        return horizontalCards?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater
        val binding =
            ChildRowListBinding.inflate(LayoutInflater.from(container.context), container, false)
        var itemData = horizontalCards?.get(position)

        binding.title.text = itemData?.title
        binding.tvInfo.text = itemData?.info
        binding.tvSubInfo.text = getSubInfo(itemData)

        binding.tvOrgName.text = itemData?.assigned_to?.org_name
        binding.tvAssignedTo.text = itemData?.assigned_to?.name

        val viewPager = container as ViewPager
        viewPager.addView(binding.root, 0)
        return binding.root
    }

    private fun getSubInfo(itemData: HorizontalCard?): String? {
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


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }
}
