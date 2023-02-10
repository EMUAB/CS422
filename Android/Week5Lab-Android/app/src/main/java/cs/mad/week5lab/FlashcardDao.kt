package cs.mad.week5lab

import androidx.room.*
import cs.mad.week5lab.entities.Flashcard
import cs.mad.week5lab.entities.FlashcardSet
import cs.mad.week5lab.entities.relations.FlashcardSetWithFlashcards

@Dao
interface FlashcardDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSets(vararg flashcardSet: FlashcardSet)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCards(vararg flashcard: Flashcard)

    @Transaction
    @Query("SELECT * FROM flashcardSet WHERE setTitle = :setTitle")
    suspend fun getFlashcardSetWithFlashcards(setTitle: String): List<FlashcardSetWithFlashcards>

    @Transaction
    @Query("SELECT * FROM flashcardSet")
    suspend fun getAllFlashcardSets(): List<FlashcardSet>

    @Delete(FlashcardSet::class)
    suspend fun deleteFlashcardSet(setTitle: String)

    @Delete(Flashcard::class)
    suspend fun deleteFlashcard(flashcard: Flashcard)

    @Update
    suspend fun updateFlashcard(flashcard: Flashcard)

    @Update
    suspend fun updateFlashcardSet(flashcardSet: FlashcardSet)

}