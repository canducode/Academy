package com.ngoopy.academy.ui.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ngoopy.academy.data.source.local.entity.CourseEntity
import com.ngoopy.academy.data.AcademyRepository
import com.ngoopy.academy.vo.Resource

class AcademyViewModel(private val academyRepository : AcademyRepository) : ViewModel() {
    fun getCourses(): LiveData<Resource<PagedList<CourseEntity>>> = academyRepository.getAllCourses()
}