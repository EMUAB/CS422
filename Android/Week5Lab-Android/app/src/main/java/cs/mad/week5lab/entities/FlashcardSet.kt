package cs.mad.week5lab.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlashcardSet(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    var setTitle: String
)