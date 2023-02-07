package cs.mad.week4lab.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.google.android.material.snackbar.Snackbar
import cs.mad.week4lab.databinding.ActivityStudySetBinding
import cs.mad.week4lab.entities.getFlashcards
import kotlin.math.abs

class StudySetActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var binding: ActivityStudySetBinding
    private lateinit var mDetector: GestureDetectorCompat

    private val flashcards = getFlashcards().toMutableList()
    private var currentPos = 0
    private var missedCount = 0
    private var correctCount = 0
    private val totalCount = flashcards.size


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudySetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentCard = binding.currentCard
        currentCard.text = flashcards[currentPos].term

        binding.completedCount.text = "0/$totalCount"

        val skipButton = binding.skipButton
        skipButton.setOnClickListener {
            skip()
        }

        val missButton = binding.missButton
        missButton.setOnClickListener {
            miss()
        }

        val correctButton = binding.correctButton
        correctButton.setOnClickListener {
            correct(it)
        }

        mDetector = GestureDetectorCompat(this, this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    private fun correct(view: View) {
        if (flashcards.size > 1) {
            if (!flashcards[currentPos].isMissed) {
                correctCount += 1
                binding.correctCount.text = "Correct: $correctCount"
            }
            flashcards.removeAt(currentPos)
            currentPos += -1
            updateCard()
            // Update completed count
            val completed = totalCount - flashcards.size
            binding.completedCount.text = "$completed/$totalCount"
        } else {
            quitStudying(view)
        }
    }

    private fun miss() {
        if (!flashcards[currentPos].isMissed) {
            flashcards[currentPos].isMissed = true
            missedCount += 1
            binding.missedCount.text = "Missed: $missedCount"
        }
        updateCard()
    }

    private fun skip() {
        updateCard()
    }

    // Increments card pos and displays new card
    private fun updateCard() {
        currentPos = (currentPos + 1) % flashcards.size
        binding.currentCard.text = flashcards[currentPos].term
    }

    fun quitStudying(view: View) {
        finish()
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        binding.currentCard.text = flashcards[currentPos].definition
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        distX: Float,
        distY: Float
    ): Boolean {
        val xDeadzone = 800
        val yDeadzone = 400
        try {
            if (abs(distX) > abs(distY) && abs(distX) > xDeadzone) {
                if (distX > 0) { // Swipe right
                    correct(binding.root)
                } else if (distX < 0) { // Swipe left
                    miss()
                }
            } else if (abs(distY) > abs(distX) && abs(distY) > yDeadzone) {
                if (distY < 0) { // Swipe up
                    skip()
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }

    override fun onDown(p0: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent) {}

    override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onLongPress(p0: MotionEvent) {}


}