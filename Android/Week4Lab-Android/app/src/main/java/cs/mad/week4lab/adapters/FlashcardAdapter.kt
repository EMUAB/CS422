package cs.mad.week4lab.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import cs.mad.week4lab.R
import cs.mad.week4lab.entities.Flashcard

class FlashcardAdapter(dataSet: List<Flashcard>) :
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
            // on flashcard clicked
            val detailsAlert = AlertDialog.Builder(it.context)
            detailsAlert.setTitle(flashcard.term).setMessage(flashcard.definition)
            detailsAlert.setPositiveButton("Edit") { dialogInterface: DialogInterface, _: Int ->
                editDialog(it, flashcard, position)
                dialogInterface.dismiss()
            }
            detailsAlert.create().show()
        }
    }

    override fun getItemCount() = data.size

    private fun editDialog(view: View, flashcard: Flashcard, position: Int) {
        val editAlert = AlertDialog.Builder(view.context)

        // Creates the title EditText and body EditText
        val title = EditText(view.context)
        title.setText(flashcard.term)
        title.hint = "Term"
        val body = EditText(view.context)
        body.setText(flashcard.definition)
        body.hint = "Definition"
        editAlert.setCustomTitle(title)
        editAlert.setView(body)

        // Sets the dialog's buttons
        editAlert.setPositiveButton("Done") { dialogInterface: DialogInterface, _: Int ->
                flashcard.term = title.text.toString()
                flashcard.definition = body.text.toString()
                notifyDataSetChanged()
                dialogInterface.dismiss()
        }
        editAlert.setNegativeButton("Delete") { dialogInterface: DialogInterface, _: Int ->
            removeAt(position)
            notifyDataSetChanged()
            dialogInterface.dismiss()
        }
        editAlert.create().show()
    }

    fun add(flashcard: Flashcard) {
        data.add(flashcard)
        notifyItemInserted(data.size - 1)
    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyDataSetChanged()
    }
}