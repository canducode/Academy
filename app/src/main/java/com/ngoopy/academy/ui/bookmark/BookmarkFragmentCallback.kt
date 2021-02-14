package com.ngoopy.academy.ui.bookmark

import com.ngoopy.academy.data.source.local.entity.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
