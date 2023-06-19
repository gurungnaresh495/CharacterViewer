package com.example.simpsonsviewer.utils

import android.content.Context
import android.content.res.Configuration
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.simpsonsviewer.R
import com.squareup.picasso.Picasso

object ScreenUtils {
    fun isTablet(context: Context): Boolean {
        return ((context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        if (imageUrl.isNullOrEmpty())
            view.setImageResource(R.drawable.ic_launcher_foreground)
        else
            Picasso.get().load(imageUrl).placeholder(R.drawable.ic_launcher_foreground).into(view)
    }
}

