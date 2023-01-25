//
//  FlashcardSetDetailViewController.swift
//  Week3Lab
//
//  Created by Andrew Taylor on 1/22/23.
//

import UIKit

class FlashcardSetDetailViewController: UIViewController, UITableViewDelegate, UITableViewDataSource  {

    var flashcards = getFlashcards()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // use the below to set the dataSource and delegate
        
        //tableView.dataSource = self
        //tableView.delegate = self
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        //return number of items
        return -1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FlashcardCell", for: indexPath) as! FlashcardTableCell
        
        //setup cell display here -- use indexPath.row to get position
        
        return cell
    }
}
