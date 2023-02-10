package cs.mad.week5lab.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.mad.week5lab.FlashcardDao
import cs.mad.week5lab.R
import cs.mad.week5lab.entities.Flashcard
import cs.mad.week5lab.runOnIO

class FlashcardAdapter(
    dataSet: List<Flashcard>,
    private val dao: FlashcardDao,
    private val setTitle: String
) :
    RecyclerView.Adapter<FlashcardAdapter.ViewHolder>() {
    private val data = dataSet.toMutableList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.term_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.flashcard_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flashcard = data[position]
        holder.textView.text = flashcard.term

        holder.itemView.setOnClickListener {
            val alert = AlertDialog.Builder(it.context)
                .setTitle(flashcard.term)
                .setMessage(flashcard.definition)
                .setPositiveButton("Edit") { dialog, _ ->
                    showEditDialog(it.context, position)
                    dialog.dismiss()
                }
                .create()
            alert.show()
        }
    }

    override fun getItemCount() = data.size

    fun updateAddCard(flashcards: List<Flashcard>) {
        data.clear()
        data.addAll(flashcards)
        notifyItemInserted(data.size - 1)
    }

    fun updateRemoveCard(flashcards: List<Flashcard>) {
        data.clear()
        data.addAll(flashcards)
        notifyDataSetChanged()
    }

    private fun showEditDialog(context: Context, position: Int) {
        val flashcard = data[position]
        val editTitle = EditText(context)
        val editBody = EditText(context)
        editTitle.setText(flashcard.term)
        editBody.setText(flashcard.definition)

        val alert = AlertDialog.Builder(context)
            .setCustomTitle(editTitle)
            .setView(editBody)
            .setNegativeButton("Delete") { dialog, _ ->
                runOnIO {
                    dao.deleteFlashcard(dao.getFlashcardSetWithFlashcards(setTitle)[0].flashcards[position])
                    updateRemoveCard(dao.getFlashcardSetWithFlashcards(setTitle)[0].flashcards)
                }
                dialog.dismiss()
            }
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Done") { dialog, _ ->
                runOnIO {
                    dao.updateFlashcard(
                        Flashcard(
                            dao.getFlashcardSetWithFlashcards(setTitle)[0].flashcards[position].uid,
                            editTitle.text.toString(),
                            editBody.text.toString(),
                            setTitle
                        )
                    )
                }
                notifyItemChanged(position)
                dialog.dismiss()
            }
            .create()
        alert.show()
    }
}