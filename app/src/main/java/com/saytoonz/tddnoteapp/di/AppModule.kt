package com.saytoonz.tddnoteapp.di

import android.app.Application
import androidx.room.Room
import com.saytoonz.tddnoteapp.feature_note.data.data_source.NoteDao
import com.saytoonz.tddnoteapp.feature_note.data.data_source.NoteDatabase
import com.saytoonz.tddnoteapp.feature_note.data.data_source.NoteDatabase.Companion.DATABASE_NAME
import com.saytoonz.tddnoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.saytoonz.tddnoteapp.feature_note.domain.repository.NoteRepository
import com.saytoonz.tddnoteapp.feature_note.domain.use_case.*
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
    fun  providesNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(db: NoteDatabase) : NoteRepository {
        return NoteRepositoryImpl(dao = db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return  NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}