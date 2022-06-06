//
//  HouseInfoRepoitoriable.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/02.
//

protocol SearchResultRepoitoriable {
    var networkManager: NetworkManagable? { get }
    func fetchHouseInfo<T: Codable>(endpoint: Endpointable, onCompleted: @escaping (T?) -> Void)
}
