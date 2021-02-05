package com.ngoopy.academy.ui.bookmark

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ngoopy.academy.R
import com.ngoopy.academy.data.CourseEntity
import com.ngoopy.academy.databinding.ItemsBookmarkBinding
import com.ngoopy.academy.ui.detail.DetailCourseActivity

class BookmarkAdapter(private val callback: BookmarkFragmentCallback) : RecyclerView.Adapter<BookmarkAdapter.CardViewHolder>() {
    private val listCourses = ArrayList<CourseEntity>()

    fun setCourses(courses: List<CourseEntity>?) {
        if (courses == null) return
        listCourses.clear()
        listCourses.addAll(courses)
    }

    class CardViewHolder(val binding: ItemsBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: CourseEntity) {
            with(binding) {
                tvItemTitle.text = course.title
                tvItemDescription.text = course.description
                tvItemDate.text = root.resources.getString(R.string.deadline_date, course.deadline)
                root.setOnClickListener {
                    val intent = Intent(root.context, DetailCourseActivity::class.java).apply {
                        putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
                    }
                    root.context.startActivity(intent)
                }
                Glide.with(root.context)
                    .load(course.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val viewBind = ItemsBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(viewBind)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(listCourses[position])
        holder.binding.imgShare.setOnClickListener {
            callback.onShareClick(listCourses[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listCourses.size
}