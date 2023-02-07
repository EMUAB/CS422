//
//  StudySetViewController.swift
//  Week4Lab
//
//  Created by Andrew Taylor on 1/29/23.
//

import UIKit

class StudySetViewController: UIViewController  {
    
    var flashcards = getFlashcards()
    var currentPos = 0
    var missCount = 0
    var correctCount = 0
    var completedCount = 0
    var totalCount = 0
    
    @IBOutlet weak var completedLabel: UILabel!
    @IBOutlet weak var correctLabel: UILabel!
    @IBOutlet weak var missedLabel: UILabel!
    @IBOutlet weak var termLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        totalCount = flashcards.count
        completedLabel.text = "0/\(totalCount)"
        termLabel.text = flashcards[0].term
    }
    
    @IBAction func screenSwiped(_ sender: UISwipeGestureRecognizer) {
        
    }
    @IBAction func screenPressed(_ sender: Any) {
        termLabel.text = flashcards[currentPos].definition
    }
    @IBAction func skipPressed(_ sender: Any) {
        nextTerm()
    }
    @IBAction func missedPressed(_ sender: Any) {
        flashcards[currentPos].missed = true
        missCount += 1
        missedLabel.text = "Missed: \(missCount)"
        nextTerm()
    }
    @IBAction func correctPressed(_ sender: Any) {
        if !flashcards[currentPos].missed {
            correctCount += 1
            correctLabel.text = "Correct: \(correctCount)"
        }
        completedCount += 1
        completedLabel.text = "\(completedCount)/\(totalCount)"
        flashcards.remove(at: currentPos)
        currentPos += -1
        nextTerm()
    }
    
    func nextTerm() {
        if (flashcards.count > 0) {
            currentPos = (currentPos + 1) % flashcards.count
            termLabel.text = flashcards[currentPos].term
        } else {
            dismiss(animated: true)
            termLabel.text = "shouldn't be readable"
        }
    }
    
    func quitStudying() {
        self.dismiss(animated: true)
    }
}
