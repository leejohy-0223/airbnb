//
//  Spots.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/06.
//

import Foundation

struct NearSpot: Hashable, Codable {
    let image: String
    let spotName: String
    let distance: Int
    let ID = UUID()
}
