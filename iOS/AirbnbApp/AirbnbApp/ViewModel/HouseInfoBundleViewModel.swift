//
//  houseInfoBundleViewModel.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/01.
//

import Foundation
import Alamofire

final class HouseInfoBundleViewModel {
    private(set) var houseInfoBundle: [HouseInfo] = []
    private(set) var changedHeartIndex: Observable<Int> = Observable(0)
    
    private var repository: HouseInfoRepository = HouseInfoRepository(networkManager: NetworkManager(sessionManager: .default))
    
    init(repository: HouseInfoRepository) {
        self.repository = repository
    }
    
    func changeIsWish(_ cardIndex: Int?) {
        guard let cardIndex = cardIndex else { return }
        // out of range 방지
            if houseInfoBundle.checkIsSafeIndex(index: cardIndex) {
                let beforeValue = self.houseInfoBundle[cardIndex].isWish
                self.houseInfoBundle[cardIndex].isWish = !beforeValue
                
                self.changedHeartIndex.value = cardIndex
        }
    }
    
    func fetchData(endpoint: Endpointable, onCompleted: @escaping ([HouseInfo]?) -> Void) {
        repository.fetchHouseInfo(endpoint: endpoint) { [weak self] (houseData: [HouseInfo]?) in
            guard let self = self else { return }
            self.houseInfoBundle = houseData ?? []
            onCompleted(houseData)
        }
    }
}
