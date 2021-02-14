package com.ngoopy.academy.di

import android.content.Context

import com.ngoopy.academy.data.AcademyRepository
import com.ngoopy.academy.data.source.local.LocalDataSource
import com.ngoopy.academy.data.source.local.room.AcademyDatabase
import com.ngoopy.academy.data.source.remote.RemoteDataSource
import com.ngoopy.academy.utils.AppExecutors
import com.ngoopy.academy.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): AcademyRepository {

        val database = AcademyDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.academyDao())
        val appExecutors = AppExecutors()

        return AcademyRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}