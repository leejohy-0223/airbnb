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
    var image: String = "https://www.google.com/imgres?imgurl=https%3A%2F%2Fimg.seoul.co.kr%2Fimg%2Fupload%2F2017%2F05%2F08%2FSSI_20170508145003.jpg&imgrefurl=https%3A%2F%2Fwww.seoul.co.kr%2Fnews%2FnewsView.php%3Fid%3D20170508500089&tbnid=xFVNP3uwjSR-_M&vet=12ahUKEwisk--ko534AhW1I6YKHbtiBFQQMygHegUIARDKAQ..i&docid=03UJrLKsj7TmwM&w=1200&h=632&q=%EC%8A%AC%ED%94%88%20%ED%8E%98%ED%8E%98&ved=2ahUKEwisk--ko534AhW1I6YKHbtiBFQQMygHegUIARDKAQ"
    let spotName: String
    var distance: Int? = nil
    var ID = UUID()
}

struct Recommend: Hashable, Codable {
    let image: String
    let name: String
    var ID = UUID()
}
