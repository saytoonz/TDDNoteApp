package com.saytoonz.tddnoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.saytoonz.tddnoteapp.feature_note.data.repository.FakeNoteRepository
import com.saytoonz.tddnoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteTest {

    private lateinit var getNote: GetNote
    private lateinit var fakeNoteRepository: FakeNoteRepository


    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNote = GetNote(fakeNoteRepository)

        val fakeList = mutableListOf<Note>()
        (0..5).forEach {
            fakeList.add(
                Note(
                    id = it,
                    title = it.toString(),
                    content = it.toString(),
                    timestamp = it.toLong(),
                    color = it
                )
            )
        }
        fakeList.shuffle()
       runBlocking {
           fakeList.forEach {
               fakeNoteRepository.insertNote(it)
           }
       }
    }


    @Test
    fun `get an existing note`() = runBlocking {
        val noteId = 1;
        val note = getNote(noteId)

        assertThat(note).isNotNull()
        assertThat(note!!.id).isEqualTo(noteId)
    }

    @Test
    fun `get a null for getting and un-exiting note`() = runBlocking {
        val note = getNote(20)

        assertThat(note).isNull()
    }

}