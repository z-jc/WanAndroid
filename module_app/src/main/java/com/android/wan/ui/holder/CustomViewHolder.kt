package com.android.wan.ui.holder

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.android.wan.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ms.banner.holder.BannerViewHolder

/**
 * FileName: CustomViewHolder
 * Author: admin
 * Date: 2020/6/23 11:15
 * Description:
 */
class CustomViewHolder : BannerViewHolder<Any?> {
    private var mImageView: ImageView? = null
    @SuppressLint("InflateParams")
    override fun createView(context: Context?, position: Int, data: Any?): View {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.banner_item_home, null)
        mImageView = view.findViewById(R.id.image)
        if (context != null) {
            Glide.with(context).load(data.toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageView!!)
        }
        return view
    }
}