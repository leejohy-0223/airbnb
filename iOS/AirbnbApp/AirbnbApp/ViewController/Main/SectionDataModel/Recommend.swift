//
//  Recommend.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

import Foundation

struct Recommend: Hashable, Codable {
    let image: String
    let name: String
    let ID = UUID()
}
