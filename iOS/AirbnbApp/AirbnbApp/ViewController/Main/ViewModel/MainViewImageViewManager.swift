//
//  MainViewImageViewManager.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

import Foundation
import OSLog

final class MainViewImageViewManager {
    private var repository:Repoitoriable?
    
    init(repository: Repoitoriable) {
        self.repository = repository
    }
    
    func fetchImage(image: String, onCompleted: @escaping (Data?) -> Void) {
        repository?.networkManager?.requestImage(url: image, completion: { response in
            switch response.result {
            case .success(let data):
                onCompleted(data)
            case .failure(let error):
                os_log(.error, "\(error.localizedDescription)")
            }
        })
    }
}
