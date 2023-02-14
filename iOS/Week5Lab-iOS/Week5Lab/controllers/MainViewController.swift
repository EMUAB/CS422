//
//  ViewController.swift
//
//  Created by Andrew Taylor on 1/22/23.
//

import UIKit
import CoreData

class MainViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource  {
    
    @IBOutlet weak var collectionView: UICollectionView!
    var sets: [FlashcardSet] = []
    var clickedSetID: NSManagedObjectID = NSManagedObjectID()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        loadData()
        
        collectionView.delegate = self
        collectionView.dataSource = self
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        //return number of items
        return sets.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "FlashcardSetCell", for: indexPath) as! FlashcardSetCollectionCell
        //setup cell display here
        cell.myLabel.text = sets[indexPath.row].title
        return cell
    }
    
    @IBAction func unwind(_ segue: UIStoryboardSegue) {
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        if (segue.identifier == "GoToDetail"){
            let destinationVC = segue.destination as? FlashcardSetDetailViewController
            destinationVC?.owningSetID = clickedSetID
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        //go to new view
        clickedSetID = sets[indexPath.row].objectID
        performSegue(withIdentifier: "GoToDetail", sender: self)
    }
    
    @IBAction func addFlashcardSet(_ sender: Any) {
        let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
        
        let flashcardSet = NSEntityDescription.insertNewObject(forEntityName: "FlashcardSet", into: context) as! FlashcardSet
        flashcardSet.title = "Set \(sets.count + 1)"
        do {
            try context.save()
        } catch {}
        
        loadData()
        
        collectionView.reloadData()
        collectionView.scrollToItem(at: IndexPath(item:sets.count-1, section: 0), at: .bottom, animated: true)
    }
    
    func loadData() {
        let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
        
        sets.removeAll()
        
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "FlashcardSet")
        do {
            let dbItems = try context.fetch(fetchRequest) as! [FlashcardSet]
            sets.append(contentsOf: dbItems)
            collectionView.reloadData()
        } catch {}
    }
}
