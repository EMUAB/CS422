package cs.mad.week5lab.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Flashcard(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val term: String,
    val definition: String,
    val setTitle: String
)

//fun getFlashcards(): List<Flashcard> {
//    val flashcards = mutableListOf<Flashcard>()
//    for (i in 0..9) {
//        flashcards.add(Flashcard(i, "Term $i", "Definition $i"))
//    }
//    return flashcards
//}