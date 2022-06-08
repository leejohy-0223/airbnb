//
//  HouseInfo.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/25.
//

struct HouseInfo: Codable, Equatable {
    let id: Int
    let name: String
    let price: Int
    let detailInfo: DetailInfo
    let latitude: Double
    let longitude: Double
    let images: String?
    let hostName: String
    var isWish: Bool
}

struct DetailInfo: Codable, Equatable {
    let maxNumber: Int
    let type: String
    let roomIntroduction: String
    let rate: Double
    let commentCount: Int
}
