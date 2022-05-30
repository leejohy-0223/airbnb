//
//  HouseInfoUnitTest.swift
//  HouseInfoUnitTest
//
//  Created by 박진섭 on 2022/05/30.
//

import XCTest

class HouseInfoUnitTest: XCTestCase {

    var sut: HouseInfoManager!
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        sut = HouseInfoManager(houseInfoBundle: [
            HouseInfo(name: "킹왕짱 숙소", detail: Detail(rating: 4.45, reviewCount: 121), price: 101231, hostingBy: "김씨", latitude: 37.490765, longitude: 127.033433),
            HouseInfo(name: "킹짱 숙소", detail: Detail(rating: 4.35, reviewCount: 121), price: 1032131, hostingBy: "김씨", latitude: 37.480765, longitude: 127.032433),
            HouseInfo(name: "왕짱 숙소", detail: Detail(rating: 4.25, reviewCount: 121), price: 10141244, hostingBy: "김씨", latitude: 37.47065, longitude: 127.031433),
            HouseInfo(name: "한국 어딘가의 아주 근사한 숙소인데 이름이 조금 길어 근데 좋은데니까 한번 눌러", detail: Detail(rating: 4.15, reviewCount: 121), price: 10001, hostingBy: "김씨", latitude: 37.490765, longitude: 127.037433)
        ])
    }

    override func tearDownWithError() throws {
        sut = nil
        try super.tearDownWithError()
    }

    func testDidChangeIsWish() {
        let cardIndex: Int = 1
        
        let beforValue: Bool = sut.houseInfoBundle[cardIndex].isWish
        
        sut.didChangeIsWish(cardIndex) { houseInfoBundle in
            let afterValue = houseInfoBundle[cardIndex].isWish
            XCTAssertFalse(beforValue == afterValue) 
        }
    }
}
