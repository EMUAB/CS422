package cs.mad.week5lab

import androidx.room.*
import cs.mad.week5lab.entities.Flashcard
import cs.mad.week5lab.entities.FlashcardSet
import cs.mad.week5lab.entities.relations.FlashcardSetWithFlashcards

@Dao
interface FlashcardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSets(vararg flashcardSet: FlashcardSet)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCards(vararg flashcard: Flashcard)

    @Transaction
    @Query("SELECT * FROM flashcardSet WHERE setTitle = :setTitle")
    suspend fun getFlashcardSetWithFlashcards(setTitle: String): List<FlashcardSetWithFlashcards>

    @Transaction
    @Query("SELECT * FROM flashcardSet")
    suspend fun getAllFlashcardSets(): List<FlashcardSet>

    @Transaction
    @Query("SELECT * FROM flashcard WHERE uid = :uid")
    suspend fun getFlashcardByUID(uid: Int): Flashcard

    @Delete(FlashcardSet::class)
    suspend fun deleteFlashcardSet(flashcardSet: FlashcardSet)

    @Delete(Flashcard::class)
    suspend fun deleteFlashcard(flashcard: Flashcard)

    @Update
    suspend fun updateFlashcard(flashcard: Flashcard)

    @Update
    suspend fun updateFlashcardSet(flashcardSet: FlashcardSet)

}