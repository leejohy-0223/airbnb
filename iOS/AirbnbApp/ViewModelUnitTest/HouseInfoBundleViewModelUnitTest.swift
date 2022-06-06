//
//  ViewModelUnitTest.swift
//  ViewModelUnitTest
//
//  Created by 박진섭 on 2022/06/02.
//

import XCTest
import Alamofire

class HouseInfoBundleViewModelUnitTest: XCTestCase {

    var sut: SearchResultViewModel!
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        setMockViewModel()
    }

    override func tearDownWithError() throws {
        sut = nil
        try super.tearDownWithError()
    }

    func testFetchData() {
        let bundle = Bundle(for: Self.self)
        
        guard let mockDataURL = bundle.url(forResource: "MockHouseInfoData", withExtension: "json"),
              let mockData = try? Data(contentsOf: mockDataURL) else {
            XCTFail("Fail to get data")
            return
        }
        
        guard let expectedData = try? JSONDecoder().decode([HouseInfo].self, from: mockData) else {
            XCTFail("Fail To Decode Data")
            return
        }
        
        let mockEndPoint = EndPointCase.getHousesInfo.endpoint
        let expectation = XCTestExpectation(description: "Fetching~")
        sut.fetchData(endpoint: mockEndPoint) { [weak self] houseInfoBundle in
            XCTAssertEqual(self?.sut.houseInfoBundle, expectedData)
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 1)
    }

    func testChangeIsWish() {

        let mockEndPoint = EndPointCase.getHousesInfo.endpoint
        let expectation = XCTestExpectation(description: "Fecthing~")
        let mockCardIndex: Int = 1
        
        sut.fetchData(endpoint: mockEndPoint) { [weak self] _ in
            self?.sut.changeIsWish(mockCardIndex)
            XCTAssertEqual(self?.sut.changedHeartIndex.value, mockCardIndex)
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 1)
    }
    
    
    func setMockViewModel()  {
        let bundle = Bundle(for: Self.self)
        
        guard let mockDataURL = bundle.url(forResource: "MockHouseInfoData", withExtension: "json"),
              let mockData = try? Data(contentsOf: mockDataURL) else {
            XCTFail("Fail to get data")
            return
        }
        
        // mockURL
        let mockEndpoint = EndPointCase.getHousesInfo.endpoint
        guard let mockURL = try? mockEndpoint.getURL().asURL() else {
            XCTFail("Fail to get url")
            return
        }

        // Mock loadingHander 설정
        URLMockProtocol.loadingHandler = { request in
            let response = HTTPURLResponse(
                url: mockURL,
                statusCode: 200,
                httpVersion: nil,
                headerFields: nil)!
            
            if request.url == mockURL {
                return (response, mockData, nil)
            } else {
                return (response, nil, nil)
            }
        }
        
        // Mock URLProtocol 주입
        let config = URLSessionConfiguration.ephemeral
        config.protocolClasses = [URLMockProtocol.self]
        self.sut = SearchResultViewModel(
            repository: SearchResultRepository(networkManager: NetworkManager(sessionManager: Session(configuration: config))))
    }
}
