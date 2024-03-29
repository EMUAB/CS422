package cs.mad.week3lab

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.mad.week3lab.entities.FlashcardSet
import cs.mad.week3lab.entities.getFlashcardSets

class FlashcardSetAdapter: RecyclerView.Adapter<FlashcardSetAdapter.ViewHolder>() {
    private val data = getFlashcardSets().toMutableList()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var textview = view.findViewById<TextView>(R.id.title_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.flashcard_set_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // launches detail activity on click
        holder.itemView.setOnClickListener {
            it.context.startActivity(Intent(it.context, FlashcardSetDetailActivity::class.java))
        }

        // connect data to view
        holder.textview.text = data[position].title
    }

    override fun getItemCount(): Int {
        // how many items in list
        return data.count()
    }

    fun addSet() {
        data.add(FlashcardSet("Set " + (data.count()+1)))
        notifyItemInserted(data.count())
    }

}