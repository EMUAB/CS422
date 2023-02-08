package cs.mad.week5lab

import androidx.room.*
import cs.mad.week5lab.entities.Flashcard
import cs.mad.week5lab.entities.FlashcardSet
import cs.mad.week5lab.entities.FlashcardSetWithFlashcards

@Dao
interface FlashcardDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertSets(vararg flashcardSet: FlashcardSet)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertCards(vararg flashcard: Flashcard)

    @Transaction
    @Query("SELECT * FROM flashcardSet WHERE setTitle = :setTitle")
    fun getFlashcardSetWithFlashcards(setTitle: String): List<FlashcardSetWithFlashcards>

    @Delete(FlashcardSet::class)
    fun deleteFlashcardSet(setTitle: String)

    @Delete(Flashcard::class)
    fun deleteFlashcard(uid: Int)

}