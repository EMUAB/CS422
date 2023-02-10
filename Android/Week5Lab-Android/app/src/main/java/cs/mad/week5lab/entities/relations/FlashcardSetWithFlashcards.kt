package cs.mad.week5lab.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import cs.mad.week5lab.entities.Flashcard
import cs.mad.week5lab.entities.FlashcardSet

data class FlashcardSetWithFlashcards(
    @Embedded val flashcardSet: FlashcardSet,
    @Relation(
        parentColumn = "setTitle",
        entityColumn = "setTitle"
    )
    val flashcards: List<Flashcard>
)
