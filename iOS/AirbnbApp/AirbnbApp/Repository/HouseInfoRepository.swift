//
//  HouseInfoManager.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/30.
//

import Alamofire
import OSLog

final class HouseInfoRepository: HouseInfoRepoitoriable {
    
    private(set) var networkManager:NetworkManagable?
    
    private(set) var houseInfoBundle: [HouseInfo] = []
    
    init(networkManager: NetworkManagable) {
        self.networkManager = networkManager
    }
    
    func fetchHouseInfo<T: Codable>(endpoint: Endpointable, onCompleted: @escaping (T?) -> Void) {
        networkManager?.request(endpoint: endpoint) { [weak self]  (result: DataResponse<T?, AFError>) in
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

