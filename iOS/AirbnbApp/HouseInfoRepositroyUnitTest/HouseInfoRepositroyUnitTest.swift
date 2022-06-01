    //
//  HouseInfoUnitTest.swift
//  HouseInfoUnitTest
//
//  Created by 박진섭 on 2022/05/30.
//

import XCTest

class HouseInfoRepositroyUnitTest: XCTestCase {

    var sut: HouseInfoRepository!
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        sut = HouseInfoRepository(networkManager: NetworkManager(sessionManager: .default))
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
