package com.saytoonz.tddnoteapp.feature_note.domain.use_case

import com.saytoonz.tddnoteapp.feature_note.domain.model.InvalidNoteException
import com.saytoonz.tddnoteapp.feature_note.domain.model.Note
import com.saytoonz.tddnoteapp.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title of the note can't be empty")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty")
        }

        repository.insertNote(note)
    }
}