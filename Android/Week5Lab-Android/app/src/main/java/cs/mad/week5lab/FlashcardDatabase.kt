package cs.mad.week5lab

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cs.mad.week5lab.entities.Flashcard
import cs.mad.week5lab.entities.FlashcardSet

@Database(
    entities = [
        FlashcardSet::class,
        Flashcard::class
    ],
    version = 1
)
abstract class FlashcardDatabase : RoomDatabase() {
    abstract val flashcardDao: FlashcardDao

    companion object {
        @Volatile
        private var INSTANCE: FlashcardDatabase? = null

        fun getInstance(context: Context): FlashcardDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FlashcardDatabase::class.java,
                    "flashcard_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}