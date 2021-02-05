package com.ngoopy.academy.ui.academy

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ngoopy.academy.R
import com.ngoopy.academy.data.CourseEntity
import com.ngoopy.academy.databinding.ItemsAcademyBinding
import com.ngoopy.academy.ui.detail.DetailCourseActivity

class AcademyAdapter : RecyclerView.Adapter<AcademyAdapter.AcademyHolder>() {
    private var listCourses = ArrayList<CourseEntity>()

    fun setCourses(courses: List<CourseEntity>?) {
        if (courses == null) return
        listCourses.clear()
        listCourses.addAll(courses)
    }

    class AcademyHolder(val binding: ItemsAcademyBinding) : RecyclerView.ViewHolder(binding.root) {
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
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcademyHolder {
        val viewBind = ItemsAcademyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AcademyHolder(viewBind)
    }

    override fun onBindViewHolder(holder: AcademyHolder, position: Int) {
        holder.bind(listCourses[position])
    }

    override fun getItemCount(): Int = listCourses.size
}