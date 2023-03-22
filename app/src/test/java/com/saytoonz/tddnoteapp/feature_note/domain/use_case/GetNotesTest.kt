package com.saytoonz.tddnoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.saytoonz.tddnoteapp.feature_note.data.repository.FakeNoteRepository
import com.saytoonz.tddnoteapp.feature_note.domain.model.Note
import com.saytoonz.tddnoteapp.feature_note.domain.util.NoteOrder
import com.saytoonz.tddnoteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {

    private lateinit var getNotes: GetNotes
    private lateinit var fakeNoteRepository: FakeNoteRepository


    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeNoteRepository)

        // Fixture
        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, char ->
            notesToInsert.add(
                Note(
                    title = char.toString(),
                    content = char.toString(),
                    timestamp = index.toLong(),
                    color = index,
                    id = index
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach {
                fakeNoteRepository.insertNote(it)
            }
        }
    }


    @Test
    fun `Order notes by title ascending order`() = runBlocking{
        val notes  = getNotes(noteOrder = NoteOrder.Title(OrderType.Ascending)).first()

        for (i in 0..notes.size -2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by title descending order`() = runBlocking{
        val notes  = getNotes(noteOrder = NoteOrder.Title(OrderType.Descending)).first()

        for (i in 0..notes.size -2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by date ascending order`() = runBlocking{
        val notes  = getNotes(noteOrder = NoteOrder.Date(OrderType.Ascending)).first()

        for (i in 0..notes.size -2){
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `Order notes by date descending order`() = runBlocking{
        val notes  = getNotes(noteOrder = NoteOrder.Date(OrderType.Descending)).first()

        for (i in 0..notes.size -2){
            assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `Order notes by Color ascending order`() = runBlocking{
        val notes  = getNotes(noteOrder = NoteOrder.Color(OrderType.Ascending)).first()

        for (i in 0..notes.size -2){
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }

    @Test
    fun `Order notes by Color descending order`() = runBlocking{
        val notes  = getNotes(noteOrder = NoteOrder.Color(OrderType.Descending)).first()

        for (i in 0..notes.size -2){
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }


}