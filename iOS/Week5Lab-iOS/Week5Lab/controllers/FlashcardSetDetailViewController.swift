//
//  FlashcardSetDetailViewController.swift
//
//  Created by Andrew Taylor on 1/22/23.
//

import UIKit
import CoreData

class FlashcardSetDetailViewController: UIViewController, UITableViewDelegate, UITableViewDataSource  {

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var sceneTitle: UINavigationItem!
    var flashcards: [Flashcard] = []
    var owningSetID: NSManagedObjectID = NSManagedObjectID()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
        do {
            try sceneTitle.title = (context.existingObject(with: owningSetID) as! FlashcardSet).title
        } catch{}
        
        loadData()
        
        tableView.dataSource = self
        tableView.delegate = self
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        //return number of items
        return flashcards.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FlashcardCell", for: indexPath) as! FlashcardTableCell
        cell.flashcardLabel.text = flashcards[indexPath.row].term
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let flashcard = flashcards[indexPath.row]
        let alert = UIAlertController(title: flashcard.term, message: flashcard.definition, preferredStyle: .alert)
        
        alert.addAction(UIAlertAction(title: "Edit", style: .default, handler: { _ in
            self.showEditAlert(indexPath: indexPath)
        }))
        alert.addAction(UIAlertAction(title: "Done", style: .cancel))
        
        present(alert, animated: true)
    }
    
    // swipe to delete
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            flashcards.remove(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .fade)
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        if (segue.identifier == "GoToStudy"){
            let destinationVC = segue.destination as? StudySetViewController
            destinationVC?.owningSetID = self.owningSetID
        }
    }
    
    @IBAction func studyClicked(_ sender: Any) {
        performSegue(withIdentifier: "GoToStudy", sender: self)
    }
    
    @IBAction func deleteClicked(_ sender: Any) {
        performSegue(withIdentifier: "UnwindToSets", sender: self)
    }
    
    @IBAction func addFlashcard(_ sender: Any) {
        let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext

        let newCard = NSEntityDescription.insertNewObject(forEntityName: "Flashcard", into: context) as! Flashcard
        newCard.term = "Term \(flashcards.count + 1)"
        newCard.definition = "Definition \(flashcards.count + 1)"
        do {
            try newCard.owningSet = (context.existingObject(with: owningSetID) as! FlashcardSet)
            try context.save()
        } catch {}
        loadData()
        
        tableView.scrollToRow(at: IndexPath(item: flashcards.count-1, section: 0), at: .bottom, animated: true)
    }
    
    func loadData() {
        let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
        
        flashcards.removeAll()
        
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Flashcard")
        do {
            let dbItems = try context.fetch(fetchRequest) as! [Flashcard]
            dbItems.forEach { card in
                if (card.owningSet?.objectID == owningSetID) {
                    flashcards.append(card)
                }
            }

            tableView.reloadData()
        } catch {}
    }
    
    func showEditAlert(indexPath: IndexPath) {
        let flashcard = flashcards[indexPath.row]
        let editAlert = UIAlertController(title: nil, message: nil, preferredStyle: .alert)
        editAlert.addTextField { textField in
            textField.text = flashcard.term
        }
        editAlert.addTextField { textField in
            textField.text = flashcard.definition
        }
        editAlert.addAction(UIAlertAction(title: "Cancel", style: .cancel))
        editAlert.addAction(UIAlertAction(title: "Delete", style: .destructive, handler: { _ in
            let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
            do {
                context.delete(self.flashcards[indexPath.row])
                try context.save()
            } catch {}
            self.loadData()
        }))
        editAlert.addAction(UIAlertAction(title: "Done", style: .default, handler: { _ in
            flashcard.term = editAlert.textFields?[0].text ?? ""
            flashcard.definition = editAlert.textFields?[1].text ?? ""
            let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
            do {
                try context.save()
            } catch {}
            
            self.tableView.reloadRows(at: [indexPath], with: .automatic)
        }))
        
        self.present(editAlert, animated: true)
    }
}
