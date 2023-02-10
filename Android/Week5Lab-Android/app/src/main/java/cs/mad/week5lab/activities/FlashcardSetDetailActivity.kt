package cs.mad.week5lab.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import cs.mad.week5lab.FlashcardDao
import cs.mad.week5lab.FlashcardDatabase
import cs.mad.week5lab.adapters.FlashcardAdapter
import cs.mad.week5lab.databinding.ActivityFlashcardSetDetailBinding
import cs.mad.week5lab.entities.Flashcard
import cs.mad.week5lab.runOnIO

class FlashcardSetDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlashcardSetDetailBinding
    private lateinit var dao: FlashcardDao
    private lateinit var setTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardSetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle = intent.getStringExtra("setTitle")!!

        dao = FlashcardDatabase.getInstance(this).flashcardDao
        var flashcards: List<Flashcard> = listOf()
        runOnIO { flashcards = dao.getFlashcardSetWithFlashcards(setTitle)[0].flashcards }

        val adapter = FlashcardAdapter(flashcards, dao, setTitle)
        binding.flashcardRecycler.adapter = adapter

        // swipe to delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                v: RecyclerView,
                h: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) {
                runOnIO {
                    dao.deleteFlashcard(dao.getFlashcardSetWithFlashcards(setTitle)[0].flashcards[h.adapterPosition])
                    adapter.updateRemoveCard(dao.getFlashcardSetWithFlashcards(setTitle)[0].flashcards)
                }
            }
        }).attachToRecyclerView(binding.flashcardRecycler)
    }

    fun addFlashcard(view: View) {
        val adapter = binding.flashcardRecycler.adapter as FlashcardAdapter
        runOnIO {
            dao.insertCards(
                Flashcard(
                    term = "New Term",
                    definition = "New Definition",
                    setTitle = setTitle
                )
            )
            adapter.updateAddCard(dao.getFlashcardSetWithFlashcards(setTitle)[0].flashcards)
        }
        binding.flashcardRecycler.smoothScrollToPosition(adapter.itemCount)
    }

    fun startStudying(view: View) {
        val intent = Intent(this, StudySetActivity::class.java)
        var flashcards: List<Flashcard> = listOf()
        runOnIO { flashcards = dao.getFlashcardSetWithFlashcards(setTitle)[0].flashcards }
        intent.putExtra("setTitle", setTitle)
        startActivity(intent)
    }
}
