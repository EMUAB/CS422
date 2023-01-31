package cs.mad.week3lab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class FlashcardSetDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard_set_detail)
        val title = findViewById<TextView>(R.id.set_title)


        val cardsRecycler = findViewById<RecyclerView>(R.id.cards_recycler)
        cardsRecycler.adapter = FlashcardAdapter()

        val addButton = findViewById<Button>(R.id.add_button)
        val adapter: FlashcardAdapter = cardsRecycler.adapter as FlashcardAdapter
        addButton.setOnClickListener {
            adapter.addCard()
            cardsRecycler.smoothScrollToPosition(adapter.itemCount)
        }

        class DeleteSwipe : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var pos = viewHolder.adapterPosition
                adapter.removeCard(pos)
            }

        }

        ItemTouchHelper(DeleteSwipe()).attachToRecyclerView(cardsRecycler)
    }
}