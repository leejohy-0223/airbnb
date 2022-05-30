//
//  HouseInfoManager.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/30.
//

import Foundation

final class HouseInfoManager {
    
    var houseInfoBundle: [HouseInfo] = []
    
    init(houseInfoBundle: [HouseInfo]) {
        self.houseInfoBundle = houseInfoBundle
    }
    
    func didChangeIsWish(_ cardIndex: Int?, completionHandler: ([HouseInfo]) -> Void) {
        guard let cardIndex = cardIndex else { return }
        // out of range 방지
        if houseInfoBundle.checkIsSafeIndex(index: cardIndex) {
            houseInfoBundle[cardIndex].isWish = !houseInfoBundle[cardIndex].isWish
        }
        completionHandler(houseInfoBundle)
    }
    
}

