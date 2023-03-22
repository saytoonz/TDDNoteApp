package com.saytoonz.tddnoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.*
import com.saytoonz.tddnoteapp.feature_note.data.repository.FakeNoteRepository
import com.saytoonz.tddnoteapp.feature_note.domain.model.InvalidNoteException
import com.saytoonz.tddnoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class AddNoteTest {

    private lateinit var addNote: AddNote
    private lateinit var fakeNoteRepository: FakeNoteRepository

    //Fixture
    val noteWithOutTitle = Note(
        title = "",
        content = "content",
        timestamp = 1L,
        color = 1,
        id = 100
    )

    val noteWithOutContent = Note(
        title = "Title",
        content = "",
        timestamp = 1L,
        color = 1,
        id = 100
    )

    val noteComplete = Note(
        title = "Title",
        content = "content",
        timestamp = 1L,
        color = 1,
        id = 100
    )

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        addNote = AddNote(fakeNoteRepository)
    }


    @Test
    fun `should throw exception for note without title`() = runBlocking {
        try {
            addNote(noteWithOutTitle)
            assert(false) { "Note was able to insert but shouldn't have because title was empty" }
        }catch (ex: InvalidNoteException){
            assertThat(ex).hasMessageThat().contains("Title")
            assertThat(ex).hasMessageThat().contains("can't be empty")
        }
    }

    @Test
    fun `should throw exception for note without content`() = runBlocking {
        try {
            addNote(noteWithOutContent)
            assert(false) { "Note was able to insert but shouldn't have because content was empty" }
        }catch (ex: InvalidNoteException){
            assertThat(ex).hasMessageThat().contains("content")
            assertThat(ex).hasMessageThat().contains("can't be empty")
        }
    }



    @Test
    fun `should successfully insert note`() = runBlocking {
        assertThat(fakeNoteRepository.notes).doesNotContain(noteComplete)
        try {
            addNote(noteComplete)
            assertThat(fakeNoteRepository.notes).contains(noteComplete)
        }catch (ex: InvalidNoteException){
            assert(false) {ex.message.toString()}
        }
    }
}