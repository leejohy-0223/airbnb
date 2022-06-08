//
//  HeroImageSectionFactory.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/06.
//

import UIKit.UICollectionView

final class HeroImageSectionFactory: MainViewSectionCreator {
    
    static func makeSectionLayout(insetValue: CGFloat) -> NSCollectionLayoutSection {
        
        let item = NSCollectionLayoutItem(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(1),
                                                                             heightDimension: .fractionalHeight(1)))
        item.contentInsets = NSDirectionalEdgeInsets(top: insetValue,
                                                     leading: insetValue,
                                                     bottom: insetValue,
                                                     trailing: insetValue)

        let group = NSCollectionLayoutGroup.horizontal(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(1),
                                                                                          heightDimension: .absolute(300)),
                                                                                          subitems: [item])
        let section = NSCollectionLayoutSection(group: group)
        return section
    }
}
