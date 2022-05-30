//
//  Extensions.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/25.
//

import UIKit
import CoreLocation

extension NSMutableAttributedString {
    func setting(string: String, fontSize: CGFloat, weight: UIFont.Weight, color: UIColor) -> NSMutableAttributedString {
        let font = UIFont.systemFont(ofSize: fontSize, weight: weight)
        let attributes: [NSAttributedString.Key: Any] = [.font: font, .foregroundColor: color]
        self.append(NSAttributedString(string: string, attributes: attributes))
        return self
    }
}

extension CLLocationCoordinate2D: Equatable {
    public static func == (lhs: CLLocationCoordinate2D, rhs: CLLocationCoordinate2D) -> Bool {
        lhs.latitude == rhs.latitude && lhs.longitude == rhs.longitude
    }
}

extension Array {
    func checkIsSafeIndex(index: Int) -> Bool {
        return indices ~= index ? true : false
    }
}


