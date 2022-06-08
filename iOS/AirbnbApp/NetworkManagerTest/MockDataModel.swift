//
//  MockDataModel.swift
//  NetworkManagerTest
//
//  Created by 박진섭 on 2022/05/31.
//

import Foundation

struct MockDataModel: Codable, Equatable {
    let userId: Int
    let id: Int
    let title: String
    let completed: Bool
}
