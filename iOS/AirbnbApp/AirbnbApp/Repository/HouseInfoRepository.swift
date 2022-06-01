//
//  HouseInfoManager.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/30.
//

import Alamofire
import OSLog

final class HouseInfoRepository {
    
    private var networkManager:NetworkManagable = NetworkManager(sessionManager: .default)
    
    init(networkManager: NetworkManagable) {
        self.networkManager = networkManager
    }
    
    private(set) var houseInfoBundle: [HouseInfo] = []
    
    func didChangeIsWish(_ cardIndex: Int?, completionHandler: ([HouseInfo]) -> Void) {
        guard let cardIndex = cardIndex else { return }
        // out of range 방지
        if houseInfoBundle.checkIsSafeIndex(index: cardIndex) {
            houseInfoBundle[cardIndex].isWish = !houseInfoBundle[cardIndex].isWish
        }
        completionHandler(houseInfoBundle)
    }
    
    func fetchHouseInfo<T: Codable>(endpoint: Endpointable, onCompleted: @escaping (T?) -> Void) {
        networkManager.request(endpoint: endpoint) { [weak self]  (result: DataResponse<T?, AFError>) in
            guard let self = self else { return }
            switch result.result {
            case .success(let data):
                self.houseInfoBundle = data as? [HouseInfo] ?? []
                onCompleted(data)
            case .failure(let error):
                os_log(.error, "\(error.localizedDescription)")
            }
        }
    }
}

