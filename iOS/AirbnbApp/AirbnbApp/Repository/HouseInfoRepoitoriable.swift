//
//  HouseInfoRepoitoriable.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/02.
//

protocol HouseInfoRepoitoriable {
    var networkManager: NetworkManagable? { get }
    var houseInfoBundle: [HouseInfo] { get }
    func fetchHouseInfo<T: Codable>(endpoint: Endpointable, onCompleted: @escaping (T?) -> Void)
}
