//
//  HouseImageView.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/27.
//

import UIKit

final class HouseImageView: UIImageView {
    
    func setImage(image: UIImage) {
        self.contentMode = .scaleAspectFit
        self.image = image
    }
}
