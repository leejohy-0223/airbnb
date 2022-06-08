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
    var image: String = "https://mblogthumb-phinf.pstatic.net/MjAxOTEwMTFfMjQ1/MDAxNTcwODA1MzU1ODkw.9ZyAgnbIhwtCLpKAPPucFyPbGJKPNcZTt1Lqj7VSd6Mg.JPNDYv6WLIfHKGUREKndPzhvxzqofhodkwdvIp3VAKUg.JPEG.sssss747/B069F215-7344-49C1-8548-70425427DC39-22206-0000104FDB5CA962_file.jpg?type=w800"
    let spotName: String
    var distance: Int? = nil
    var ID = UUID()
}

struct Recommend: Hashable, Codable {
    let image: String
    let name: String
    var ID = UUID()
}
