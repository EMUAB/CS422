package cs.mad.week5lab.entities

import androidx.room.Embedded
import androidx.room.Relation

data class FlashcardSetWithFlashcards(
    @Embedded val flashcardSet: FlashcardSet,
    @Relation(
        parentColumn = "setTitle",
        entityColumn = "setTitle"
    )
    val flashcards: List<Flashcard>
)
