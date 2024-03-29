package cs.mad.week3lab

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.mad.week3lab.entities.Flashcard
import cs.mad.week3lab.entities.getFlashcards

class FlashcardAdapter : RecyclerView.Adapter<FlashcardAdapter.ViewHolder>() {
    private val data = getFlashcards().toMutableList()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var cardText = view.findViewById<TextView>(R.id.term_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.flashcard_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // connect data to view
        holder.cardText.text = data[position].term
    }

    override fun getItemCount(): Int {
        // how many items in list
        return data.count()
    }

    fun addCard() {
        val newcount = data.count()+1
        data.add(Flashcard("Term $newcount", "Definition $newcount"))
        notifyItemInserted(data.count())
    }

    fun removeCard(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }
}