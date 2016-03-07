//
//  ViewController.swift
//  WeAllCode
//
//  Created by Wesley Coomber on 3/7/16.
//  Copyright © 2016 Wesley Coomber. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBOutlet weak var stackview: UIStackView!

    @IBOutlet weak var loginBut: UIButton!
    @IBOutlet weak var createAccBut: UIButton!
    
    @IBAction func loginButtonSample(sender: UIButton) {
        //mealNameLabel.text = "Default Text"
        
       
        //print("s as json \(s.toJsonString())")
        //mealNameLabel.text = "s as json \(s.toJsonString())"
        print("Login Button clicked!")
    }
    
    @IBAction func createAccButtonSample(sender: UIButton) {
        //mealNameLabel.text = "Default Text"
        
        
        //print("s as json \(s.toJsonString())")
        //mealNameLabel.text = "s as json \(s.toJsonString())"
        print("Create Account Button clicked!")
    }
    
    /*
    func getName() -> String {
    var tempName = "";
    tempName = self.name
    
    
    return tempName
    }
    func getStudentId() -> Int {
    var temp = -1;
    temp = self.studentid
    
    
    return temp
    }
    func getTakes() -> Array<String> {
    var tempArray = [String]()
    tempArray = self.takes;
    print("oh boy")
    
    return tempArray
    }
    */
}

