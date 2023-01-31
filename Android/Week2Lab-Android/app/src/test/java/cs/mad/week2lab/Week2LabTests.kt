package cs.mad.week2lab

import cs.mad.week2lab.entities.Flashcard
import cs.mad.week2lab.entities.FlashcardSet
import org.junit.Test
import kotlin.reflect.KProperty

class Week2LabTests {

    fun getFlashcardSets(): Array<FlashcardSet> {
        return arrayOf(
            FlashcardSet("one"),
            FlashcardSet("two"),
            FlashcardSet("three"),
            FlashcardSet("four"),
            FlashcardSet("five"),
            FlashcardSet("six"),
            FlashcardSet("seven"),
            FlashcardSet("eight"),
            FlashcardSet("nine"),
            FlashcardSet("ten")
        )
    }

    fun getFlashcards(): Array<Flashcard> {
        return arrayOf(
            Flashcard("one", "the first number"),
            Flashcard("two", "the second number"),
            Flashcard("three", "the third number"),
            Flashcard("four", "the fourth number"),
            Flashcard("five", "the fifth number"),
            Flashcard("six", "the sixth number"),
            Flashcard("seven", "the seventh number"),
            Flashcard("eight", "the eighth number"),
            Flashcard("nine", "the ninth number"),
            Flashcard("ten", "the tenth number")
        )
    }


    @Test
    fun testFlashcard() {
        assert(sizeAndType<Flashcard>(getFlashcards()))

        assert(Flashcard::term is KProperty<String>)
        assert(Flashcard::definition is KProperty<String>)
    }

    @Test
    fun testFlashcardSet() {
        assert(sizeAndType<FlashcardSet>(getFlashcardSets()))
        assert(FlashcardSet::title is KProperty<String>)
    }

    private fun <T> sizeAndType(array: Array<T>) = sizeAndType(array.asList())
    private fun <T> sizeAndType(collection: Collection<T>) = collection.size == 10
    private fun <T> sizeAndType(other: Any) = false
}