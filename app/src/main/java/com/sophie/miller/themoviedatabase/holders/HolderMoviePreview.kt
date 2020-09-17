package com.sophie.miller.themoviedatabase.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sophie.miller.themoviedatabase.databinding.ItemMovieBinding

class HolderMoviePreview(itemView: View) : ViewHolder(itemView) {
    public val binding: ItemMovieBinding;

    init {
        binding = ItemMovieBinding.bind(itemView)
    }
}