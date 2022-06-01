//
//  HouseInfo.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/25.
//

struct HouseInfo: Codable {
    let id: String = " "
    let name: String
    let detail: Detail
    let price: Int
    let hostingBy: String
    let latitude: Double
    let longitude: Double
    var isWish: Bool = false
}

struct Detail: Codable {
    let MaimumNumberOfPeople: Int = 10
    let houseForm: String = "호텔"
    let introduce: String = "반가워요 ^^"
    let rating: Double
    let reviewCount: Int
}
