package cs.mad.week5lab.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.mad.week5lab.R
import cs.mad.week5lab.activities.FlashcardSetDetailActivity
import cs.mad.week5lab.entities.FlashcardSet

class FlashcardSetAdapter(dataSet: List<FlashcardSet>): RecyclerView.Adapter<FlashcardSetAdapter.ViewHolder>() {
    private val data = dataSet.toMutableList()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.title_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.flashcard_set_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position].setTitle
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, FlashcardSetDetailActivity::class.java)
            intent.putExtra("setTitle", data[position].setTitle)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = data.size

    fun updateSets(sets: List<FlashcardSet>) {
        data.clear()
        data.addAll(sets)
        notifyItemInserted(data.size - 1)
    }
}