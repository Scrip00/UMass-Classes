package com.scrip0.umassclasses.di


import android.content.Context
import androidx.room.Room
import com.scrip0.umassclasses.db.local.LocalClassesDao
import com.scrip0.umassclasses.db.local.LocalClassesDatabase
import com.scrip0.umassclasses.repositories.UMassRepository
import com.scrip0.umassmaps.other.Constants.LOCAL_CLASSES_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Singleton
	@Provides
	fun provideLocalClassesDatabase(
		@ApplicationContext app: Context
	) = Room.databaseBuilder(
		app,
		LocalClassesDatabase::class.java,
		LOCAL_CLASSES_DATABASE_NAME
	).fallbackToDestructiveMigration().build()

	@Singleton
	@Provides
	fun provideLocalClassesDao(db: LocalClassesDatabase): LocalClassesDao = db.getClassesDao()
}