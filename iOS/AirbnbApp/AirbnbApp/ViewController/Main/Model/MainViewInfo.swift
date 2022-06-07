//
//  SectionData.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

import Foundation


struct MainViewInfo: Codable {
    let heroImage: HeroImage
    let NearSpot: [NearSpot]
    let recommend: [Recommend]
}

struct HeroImage: Hashable,Codable {
    let image: String
    var id: UUID = UUID()
}

struct NearSpot: Hashable, Codable {
    let image: String
    let spotName: String
    let distance: Int
    var ID = UUID()
}

struct Recommend: Hashable, Codable {
    let image: String
    let name: String
    var ID = UUID()
}
