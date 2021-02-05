package com.ngoopy.academy.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ngoopy.academy.data.CourseEntity
import com.ngoopy.academy.data.source.AcademyRepository

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getBookmark(): LiveData<List<CourseEntity>> = academyRepository.getBookmarkedCourses()
}