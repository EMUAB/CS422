//
//  FlashcardSetDetailViewController.swift
//  Week3Lab
//
//  Created by Andrew Taylor on 1/22/23.
//

import UIKit

class FlashcardSetDetailViewController: UIViewController, UITableViewDelegate, UITableViewDataSource  {
    
    @IBOutlet weak var tableView: UITableView!
    var flashcards = getFlashcards()
    
    @IBAction func onAddClicked(_ sender: Any) {
        flashcards.append(Flashcard(term: "Term " + String(flashcards.count + 1), definition: "Definition " + String(flashcards.count + 1)))
        tableView.reloadData()
        tableView.scrollToRow(at: IndexPath(row: flashcards.count-1, section: 0), at: UITableView.ScrollPosition.bottom, animated: true)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // use the below to set the dataSource and delegate
        
        tableView.dataSource = self
        tableView.delegate = self
    }
    
    private func deleteCard(indexPath: IndexPath) {
        if flashcards.count > 1 {
            flashcards.remove(at: indexPath.row)
            tableView.reloadData()
        }
    }
    
    // Source for below function: https://programmingwithswift.com/uitableviewcell-swipe-actions-with-swift/
    func tableView(_ tableView: UITableView,
                   trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        let delete = UIContextualAction(style: .normal,
                                        title: "Delete") { [weak self] (action, view, completionHandler) in
            self?.deleteCard(indexPath: indexPath)
            completionHandler(true)
        }
        delete.backgroundColor = .systemRed
        return UISwipeActionsConfiguration(actions: [delete])
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        //return number of items
        return flashcards.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FlashcardCell", for: indexPath) as! FlashcardTableCell
        
        //setup cell display here -- use indexPath.row to get position
        cell.label.text = flashcards[indexPath.row].term
        
        return cell
    }
}
