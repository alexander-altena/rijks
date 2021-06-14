package com.example.rijks.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.rijks.R
import kotlin.random.Random

@BindingAdapter("imageUrl", "imagePlaceholder", requireAll = false)
fun ImageView.imageUrl(url: String?, @DrawableRes placeholderId: Int?) {
    load(url) {
        crossfade(true)
        placeholder(
            placeholderId?.let {
                ContextCompat.getDrawable(context, it)
            } ?: run {
                val placeholdersColors = resources.getStringArray(R.array.placeholders)
                val placeholderColor = placeholdersColors[Random.nextInt(placeholdersColors.size)]
                ColorDrawable(Color.parseColor(placeholderColor))
            }
        )
    }
}