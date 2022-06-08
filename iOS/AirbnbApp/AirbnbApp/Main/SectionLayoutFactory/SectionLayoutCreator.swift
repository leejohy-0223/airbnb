//
//  SectionLayoutCreator.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/06.
//

import UIKit.UICollectionView

protocol SectionLayoutCreator {
    static func makeSectionLayout(insetValue: CGFloat) -> NSCollectionLayoutSection
}
