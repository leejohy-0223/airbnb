//
//  HouseNameLabel.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/27.
//

import UIKit

final class HouseNameLabel: UILabel {
 
    func setHouseNameLabel(numberOfLine: Int, fontSize: CGFloat, houseName: String) {
        self.lineBreakMode = .byTruncatingTail
        self.textColor = .label
        self.numberOfLines = numberOfLine
        self.font = .systemFont(ofSize: fontSize)
        self.text = houseName
    }
}
