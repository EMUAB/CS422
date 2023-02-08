package cs.mad.week5lab.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcardSet")
data class FlashcardSet(
    @PrimaryKey val setTitle: String
)

fun getFlashcardSets(): List<FlashcardSet> {
    val sets = mutableListOf<FlashcardSet>()
    for (i in 0..9) {
        sets.add(FlashcardSet("Set $i"))
    }
    return sets
}