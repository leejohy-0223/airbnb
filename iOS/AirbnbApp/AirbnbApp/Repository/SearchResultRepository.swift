//
//  HouseInfoManager.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/30.
//

import Alamofire
import OSLog

final class SearchResultRepository: SearchResultRepoitoriable {
    
    private(set) var networkManager:NetworkManagable?
    
    init(networkManager: NetworkManagable) {
        self.networkManager = networkManager
    }
    
    func fetchHouseInfo<T: Codable>(endpoint: Endpointable, onCompleted: @escaping (T?) -> Void) {
        networkManager?.request(endpoint: endpoint) { (result: DataResponse<T?, AFError>) in
            switch result.result {
            case .success(let data):
                onCompleted(data)
            case .failure(let error):
                os_log(.error, "\(error.localizedDescription)")
            }
        }
    }
}

