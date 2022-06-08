//
//  HouseInfoRepoitoriable.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/02.
//

protocol Repoitoriable {
    var  networkManager: NetworkManagable? { get }
    func fetchData<T: Codable>(endpoint: Endpointable, onCompleted: @escaping (T?) -> Void)
}
