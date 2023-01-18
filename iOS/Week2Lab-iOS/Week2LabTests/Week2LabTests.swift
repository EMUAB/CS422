//
//  Week2LabTests.swift
//  Week2LabTests
//
//  Created by Andrew Taylor on 1/15/23.
//

import XCTest
@testable import Week2Lab

final class Week2LabTests: XCTestCase {
    func getFlashcards() -> Array<Flashcard> {
        return [
            Flashcard(term: "one", definition: "the first number"),
            Flashcard(term: "two", definition: "the second number"),
            Flashcard(term: "three", definition: "the third number"),
            Flashcard(term: "four", definition: "the fourth number"),
            Flashcard(term: "five", definition: "the fifth number"),
            Flashcard(term: "six", definition: "the sixth number"),
            Flashcard(term: "seven", definition: "the seventh number"),
            Flashcard(term: "eight", definition: "the eighth number"),
            Flashcard(term: "nine", definition: "the ninth number"),
            Flashcard(term: "ten", definition: "the tenth number")
        ]
    }
    
    func getFlashcardSets() -> Array<FlashcardSet> {
        return [FlashcardSet(title: "one"), FlashcardSet(title: "two"), FlashcardSet(title: "three"), FlashcardSet(title: "four"), FlashcardSet(title: "five"), FlashcardSet(title: "six"), FlashcardSet(title: "seven"), FlashcardSet(title: "eight"), FlashcardSet(title: "nine"), FlashcardSet(title: "ten")]
    }
    
    func testFlashcard() throws {
        let flashcards = getFlashcards()
        assert(flashcards.count == 10)
        assert(flashcards is Array<Flashcard>)
        
        let mirror = Mirror(reflecting: flashcards[0])
        assert(mirror.children.count == 2)
        assert(
            mirror.children.contains { (label: String?, _: Any) in
                label == "term"
            }
        )
        assert(
            mirror.children.contains { (label: String?, _: Any) in
                label == "definition"
            }
        )
    }

    func testFlashcardSet() throws {
        let flashcardSets = getFlashcardSets()
        assert(flashcardSets.count == 10)
        assert(flashcardSets is Array<FlashcardSet>)
        
        let mirror = Mirror(reflecting: flashcardSets[0])
        assert(mirror.children.count == 1)
        assert(
            mirror.children.contains { (label: String?, _: Any) in
                label == "title"
            }
        )
    }
}
