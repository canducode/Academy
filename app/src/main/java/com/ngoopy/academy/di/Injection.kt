package com.ngoopy.academy.di

import android.content.Context
import com.ngoopy.academy.data.source.AcademyRepository
import com.ngoopy.academy.data.source.remote.RemoteDataSource
import com.ngoopy.academy.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): AcademyRepository {
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))

        return AcademyRepository.getInstance(remoteDataSource)
    }
}