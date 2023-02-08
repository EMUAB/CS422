package cs.mad.week5lab.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cs.mad.week5lab.FlashcardDatabase
import cs.mad.week5lab.adapters.FlashcardSetAdapter
import cs.mad.week5lab.R
import cs.mad.week5lab.databinding.ActivityMainBinding
import cs.mad.week5lab.entities.Flashcard
import cs.mad.week5lab.entities.FlashcardSet
import cs.mad.week5lab.entities.getFlashcardSets
import kotlinx.coroutines.CoroutineScope

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = FlashcardDatabase.getInstance(this).flashcardDao

        val set1 = FlashcardSet("Set 1")
        val flashcards = listOf(
            Flashcard(term = "term 1", definition = "def 1", setTitle = "Set 1"),
            Flashcard(term = "term 2", definition = "def 2", setTitle = "Set 1"),
            Flashcard(term = "term 3", definition = "def 3", setTitle = "Set 1"),
        )

        dao.insertSets(set1)
        flashcards.forEach { dao.insertCards(it) }

        binding.flashcardSetRecycler.adapter = FlashcardSetAdapter(getFlashcardSets())
    }

    fun addSet(view: View) {
        val adapter = binding.flashcardSetRecycler.adapter as FlashcardSetAdapter
        adapter.add(FlashcardSet("New Set"))
        binding.flashcardSetRecycler.smoothScrollToPosition(adapter.itemCount)
    }
}