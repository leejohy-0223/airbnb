//
//  NetworkManagerTest.swift
//  NetworkManagerTest
//
//  Created by 박진섭 on 2022/05/31.
//

import XCTest
import Alamofire

class NetworkManagerTest: XCTestCase {

    var sut: NetworkManagable!
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        sut = NetworkManager(sessionManager: .default)
    }

    override func tearDownWithError() throws {
        sut = nil
        try super.tearDownWithError()
    }

    func testRequest() throws {
        // mockData
        let bundle = Bundle(for: Self.self)
        
        guard let mockDataURL = bundle.url(forResource: "MockData", withExtension: "json"),
              let mockData = try? Data(contentsOf: mockDataURL) else {
            XCTFail("Mock Data Error")
            return
        }
        
        // expected Decoded Data with MockModel
        guard let expectedData = try? JSONDecoder().decode(MockDataModel.self, from: mockData) else {
            XCTFail("Mock Data Decoding Error")
            return
        }
        
        // mockURL
        let mockEndpoint = EndPointCase.getHousesInfo.endpoint
        guard let mockURL = try? mockEndpoint.getURL().asURL() else {
            XCTFail("Mock EndPoint Error")
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
                XCTFail("Mock LoadingHandler Error")
                return (response, nil, nil)
            }
        }
        
        // Mock URLProtocol 주입
        let config = URLSessionConfiguration.ephemeral
        config.protocolClasses = [URLMockProtocol.self]
        
        sut = NetworkManager(sessionManager: Session(configuration: config))
        
        let expectation = XCTestExpectation(description: "로딩중~")
        
        sut.request(endpoint: mockEndpoint) { (result:DataResponse<MockDataModel?, AFError>) in
            switch result.result {
            case .success(let data):
                XCTAssertEqual(data, expectedData)
            case .failure(_):
                XCTFail("Request Fail")
            }
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 1)
    }
}
