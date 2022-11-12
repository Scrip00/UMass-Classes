package com.scrip0.umassclasses.di


import com.scrip0.umassclasses.repositories.UMassRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Singleton
	@Provides
	fun providesUMassRepository() = UMassRepository()
}