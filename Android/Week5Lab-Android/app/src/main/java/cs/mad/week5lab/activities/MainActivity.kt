package cs.mad.week5lab.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cs.mad.week5lab.FlashcardDao
import cs.mad.week5lab.FlashcardDatabase
import cs.mad.week5lab.adapters.FlashcardSetAdapter
import cs.mad.week5lab.databinding.ActivityMainBinding
import cs.mad.week5lab.entities.Flashcard
import cs.mad.week5lab.entities.FlashcardSet
import cs.mad.week5lab.runOnIO

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dao: FlashcardDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = FlashcardDatabase.getInstance(this).flashcardDao

        val set1 = FlashcardSet(setTitle = "Set 1")
        val flashcards = listOf(
            Flashcard(term = "term 1", definition = "def 1", setTitle = "Set 1"),
            Flashcard(term = "term 2", definition = "def 2", setTitle = "Set 1"),
            Flashcard(term = "term 3", definition = "def 3", setTitle = "Set 1"),
        )
        runOnIO {
            dao.insertSets(set1)
            flashcards.forEach { dao.insertCards(it) }
        }
        var flashcardSets: List<FlashcardSet> = listOf()
        runOnIO { flashcardSets = dao.getAllFlashcardSets() }
        binding.flashcardSetRecycler.adapter = FlashcardSetAdapter(flashcardSets)
    }

    fun addSet(view: View) {
        val adapter = binding.flashcardSetRecycler.adapter as FlashcardSetAdapter
        runOnIO {
            dao.insertSets(FlashcardSet(setTitle = "Set ${dao.getAllFlashcardSets().size + 1}"))
            adapter.updateSets(dao.getAllFlashcardSets())
        }
        binding.flashcardSetRecycler.smoothScrollToPosition(adapter.itemCount)
    }
}