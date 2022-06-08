//
//  MainViewSectionModel.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

import Foundation

struct MainViewSectionViewModel {
    private var repository: Repoitoriable?
    
    private lazy var mockMainInfo:MainViewInfo = Constants.mockData.mainViewInfo
  
    init(repository: Repoitoriable) {
        self.repository = repository
    }
     
    mutating func fetchMockData(onCompleted: @escaping (MainViewInfo?) -> Void) {
        onCompleted(self.mockMainInfo)
    }
    
    func fetchData(endpoint:Endpointable, onCompleted: @escaping (MainViewInfo?) -> Void ) {
        self.repository?.fetchData(endpoint: endpoint) { (data: MainViewInfo?) in
            onCompleted(data)
        }
    }
}
