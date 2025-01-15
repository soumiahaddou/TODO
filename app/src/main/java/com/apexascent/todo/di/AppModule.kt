package com.apexascent.todo.di

import android.app.Application
import androidx.room.Room
import com.apexascent.todo.data.Repository
import com.apexascent.todo.data.RepositoryImpl
import com.apexascent.todo.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(application: Application):TodoDatabase{
        return Room.databaseBuilder(
            application,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideTodoRepository(database: TodoDatabase): Repository{
        return RepositoryImpl(database.dao)
    }

}