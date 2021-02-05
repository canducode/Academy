package com.ngoopy.academy.ui.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ngoopy.academy.data.CourseEntity
import com.ngoopy.academy.data.source.AcademyRepository

class AcademyViewModel(private val academyRepository : AcademyRepository) : ViewModel() {
    fun getCourses(): LiveData<List<CourseEntity>> = academyRepository.getAllCourses()
}