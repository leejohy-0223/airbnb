//
//  SearchHeaderViewModel.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/08.
//

import Foundation

final class SearchHeaderViewModel {
    private let heraderTitle:[String?] = ["근처인기여행지"]
    
    func getTitle(at sectionNumber:Int) -> String? {
        if heraderTitle.checkIsSafeIndex(index: sectionNumber) {
           return heraderTitle[sectionNumber]
        }
        return nil
    }
}
