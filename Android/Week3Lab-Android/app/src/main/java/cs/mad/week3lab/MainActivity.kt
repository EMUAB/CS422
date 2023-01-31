package cs.mad.week3lab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var setsRecycler = findViewById<RecyclerView>(R.id.sets_recycler)
        setsRecycler.adapter = FlashcardSetAdapter()

        var addButton = findViewById<Button>(R.id.add_set_button)
        addButton.setOnClickListener {
            val adapter: FlashcardSetAdapter = (setsRecycler.adapter as FlashcardSetAdapter)
            adapter.addSet()
            setsRecycler.smoothScrollToPosition(adapter.itemCount)
        }

    }
}