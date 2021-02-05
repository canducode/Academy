package com.ngoopy.academy.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ngoopy.academy.R
import com.ngoopy.academy.data.CourseEntity
import com.ngoopy.academy.databinding.ActivityDetailCourseBinding
import com.ngoopy.academy.ui.reader.CourseReaderActivity
import com.ngoopy.academy.viewmodel.ViewModelFactory

class DetailCourseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailCourseBinding

    companion object {
        const val EXTRA_COURSE = "extra_course"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = DetailCourseAdapter()

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailCourseViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val courseId = extras.getString(EXTRA_COURSE)
            if (courseId != null) {

                binding.progressBar.visibility = View.VISIBLE
                binding.content.visibility = View.INVISIBLE

                viewModel.setSelectedCourse(courseId)
                viewModel.getModules().observe(this, { modules ->
                    binding.progressBar.visibility = View.GONE
                    binding.content.visibility = View.VISIBLE

                    adapter.setModules(modules)
                    adapter.notifyDataSetChanged()
                })
                viewModel.getCourse().observe(this, {course -> populateCourse(course)})
            }
        }

        with(binding.includeLayout.rvModule) {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@DetailCourseActivity)
            setHasFixedSize(true)
            this.adapter = adapter
            val divinderItemDecoration = DividerItemDecoration(binding.includeLayout.rvModule.context, DividerItemDecoration.VERTICAL)
            addItemDecoration(divinderItemDecoration)
        }
    }

    private fun populateCourse(courseEntity: CourseEntity) {
        binding.includeLayout.textTitle.text = courseEntity.title
        binding.includeLayout.textDesc.text = courseEntity.description
        binding.includeLayout.textDate.text = resources.getString(R.string.deadline_date, courseEntity.deadline)

        Glide.with(this)
                .load(courseEntity.imagePath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.includeLayout.imagePoster)

        binding.includeLayout.btnStart.setOnClickListener {
            val intent = Intent(this, CourseReaderActivity::class.java).apply {
                putExtra(CourseReaderActivity.EXTRA_COURSE_ID, courseEntity.courseId)
            }
            startActivity(intent)
        }
    }
}