//
//  SectionHeaderViewModel.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

import Foundation

final class SectionHeaderViewModel {
    private let heraderTitle:[String?] = [nil,
                                          "가까운 여행지 둘러보기",
                                          "어디에서나,여행은\n살아보는거야!"]
    
    
    func getTitle(at sectionNumber:Int) -> String? {
        if heraderTitle.checkIsSafeIndex(index: sectionNumber) {
           return heraderTitle[sectionNumber]
        }
        return nil
    }
}
