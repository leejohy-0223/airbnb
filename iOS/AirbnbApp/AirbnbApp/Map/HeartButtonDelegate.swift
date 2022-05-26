//
//  HeartButtonDelegate.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/26.
//

import Foundation

protocol HeartButtonDelegate: AnyObject {
    func hearButtonIsTapped(_ cardIndex: Int?)
}
