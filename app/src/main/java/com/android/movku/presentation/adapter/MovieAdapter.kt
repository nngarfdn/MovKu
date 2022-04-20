package com.android.movku.presentation.adapter

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.movku.data.movie.model.Movie
import com.android.movku.databinding.ItemMovieBinding
import com.bumptech.glide.Glide


class MovieAdapter: RecyclerView.Adapter<MovieAdapter.RecentAdapterViewHolder>() {
    inner class RecentAdapterViewHolder(val view: ItemMovieBinding) :
        RecyclerView.ViewHolder(view.root)



    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(list: MutableList<Movie>) {
        differ.submitList(list) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentAdapterViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentAdapterViewHolder, position: Int) {
        holder.view.apply {
            val data = differ.currentList[position]
            txtTitle.text = data.title

            Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/w500"+data.posterPath)
                .into(imgMovie)

            val onItemClickListener :((Movie)->Unit)?=null
            root.setOnClickListener { onItemClickListener?.invoke(data) }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}