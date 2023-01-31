//
//  ViewController.swift
//  Week3Lab
//
//  Created by Andrew Taylor on 1/22/23.
//

import UIKit

class MainViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource  {
    
    
    @IBOutlet weak var collectionView: UICollectionView!
    var sets: [FlashcardSet] = getFlashcardSets()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // use the below to set the dataSource and delegate
        
        collectionView.dataSource = self
        collectionView.delegate = self
    }
    
    @IBAction func onAddClicked(_ sender: Any) {
        sets.append(FlashcardSet(title: "Title " + String(sets.count+1)))
        collectionView.reloadData()
        collectionView.scrollToItem(at: IndexPath(row: sets.count-1, section: 0), at: UICollectionView.ScrollPosition.bottom, animated: true)
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        //return number of items
        return sets.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "FlashcardSetCell", for: indexPath) as! FlashcardSetCollectionCell
        
        //setup cell display here -- use indexPath.row to get position
        cell.label.text = sets[indexPath.row].title
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        //go to new view
        performSegue(withIdentifier: "GoToDetail", sender: self)
    }
}
