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
            HouseInfo(name: "킹왕짱 숙소", detail: Detail(rating: 4.5, reviewCount: 101), price: 85000, hostingBy: "김씨", coordinate: CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.033433)),
            HouseInfo(name: "킹왕 숙소", detail: Detail(rating: 4.45, reviewCount: 121), price: 75000, hostingBy: "박씨", coordinate: CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.032433)),
            HouseInfo(name: "킹짱 숙소", detail: Detail(rating: 4.3, reviewCount: 12112), price: 65430, hostingBy: "정씨", coordinate: CLLocationCoordinate2D(latitude: 37.48065, longitude: 127.031433)),
            HouseInfo(name: "왕짱 숙소", detail: Detail(rating: 4.221, reviewCount: 1210), price: 12350, hostingBy: "송씨", coordinate: CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.030433)),
            HouseInfo(name: "한국 어딘가의 아주 근사한 숙소인데 이름이 조금 길어 근데 좋은데니까 한번 눌러", detail: Detail(rating: 4.33, reviewCount: 51), price: 12350, hostingBy: "이씨", coordinate: CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.037433))
        ])
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {

    }
}
