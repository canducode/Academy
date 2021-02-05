package com.ngoopy.academy.ui.bookmark

import com.ngoopy.academy.data.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
