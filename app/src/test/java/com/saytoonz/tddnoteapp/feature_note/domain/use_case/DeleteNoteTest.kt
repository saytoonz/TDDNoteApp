package com.saytoonz.tddnoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.saytoonz.tddnoteapp.feature_note.data.repository.FakeNoteRepository
import com.saytoonz.tddnoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class DeleteNoteTest {

    private lateinit var deleteNote: DeleteNote
    private lateinit var fakeNoteRepository: FakeNoteRepository

    //Fixture
    val note = Note(
        title = "", content = "", timestamp = 1L, color = 1, id = 100
    )

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)

        runBlocking {
            ('a'..'g').forEachIndexed { index, char ->
                fakeNoteRepository.insertNote(
                    Note(
                        title = char.toString(),
                        content = char.toString(),
                        color = index,
                        timestamp = index.toLong(),
                        id = index
                    )
                )
            }
        }
    }

    @Test
    fun `should delete a single note from existing note list`() = runBlocking {
        fakeNoteRepository.insertNote(note)
        val initialSize = fakeNoteRepository.notes.size;
        deleteNote(note)
        val lateSize = fakeNoteRepository.notes.size;

        assertThat(initialSize).isGreaterThan(lateSize)
        assertThat(fakeNoteRepository.notes).doesNotContain(note)
    }

    @Test
    fun `should try to delete un-existing note`() = runBlocking {
        val initialSize = fakeNoteRepository.notes.size;
        deleteNote(note)
        val lateSize = fakeNoteRepository.notes.size;

        assertThat(initialSize).isEqualTo(lateSize)
        assertThat(fakeNoteRepository.notes).doesNotContain(note)
    }
}