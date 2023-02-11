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
        var flashcardSets: List<FlashcardSet> = listOf()
        runOnIO { flashcardSets = dao.getAllFlashcardSets() }
        binding.flashcardSetRecycler.adapter = FlashcardSetAdapter(flashcardSets)
        binding.flashcardSetRecycler.adapter
    }

    fun addSet(view: View) {
        val adapter = binding.flashcardSetRecycler.adapter as FlashcardSetAdapter
        var allSets = listOf<FlashcardSet>()
        runOnIO {
            dao.insertSets(FlashcardSet(setTitle = "Set ${dao.getAllFlashcardSets().size + 1}"))
            allSets = dao.getAllFlashcardSets()
        }
        adapter.updateSets(allSets)
        binding.flashcardSetRecycler.smoothScrollToPosition(adapter.itemCount)
    }
}