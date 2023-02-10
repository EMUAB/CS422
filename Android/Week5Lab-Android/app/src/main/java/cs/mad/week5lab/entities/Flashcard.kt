package cs.mad.week5lab.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Flashcard(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    var term: String,
    var definition: String,
    var setTitle: String
)