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
    
    @IBAction func swipeUp(_ sender: UISwipeGestureRecognizer) {
        if sender.state == .ended{
            skip()
        }
    }
    @IBAction func swipeRight(_ sender: UISwipeGestureRecognizer) {
        if sender.state == .ended {
            correct()
        }
    }
    @IBAction func swipeLeft(_ sender: UISwipeGestureRecognizer) {
        if sender.state == .ended {
            missed()
        }
    }
    @IBAction func screenPressed(_ sender: UITapGestureRecognizer) {
        guard sender.view != nil else { return }
        
        if sender.state == .ended {
            termLabel.text = flashcards[currentPos].definition
        }
    }
    
    @IBAction func skipPressed(_ sender: Any) {
        skip()
    }
    @IBAction func missedPressed(_ sender: Any) {
        missed()
    }
    @IBAction func correctPressed(_ sender: Any) {
        correct()
    }
    
    func skip() {
        nextTerm()
    }
    
    func missed() {
        flashcards[currentPos].missed = true
        missCount += 1
        missedLabel.text = "Missed: \(missCount)"
        nextTerm()
    }
    
    func correct() {
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
            quitStudying()
        }
    }
    
    func quitStudying() {
        self.navigationController?.popViewController(animated: true)
    }
}
