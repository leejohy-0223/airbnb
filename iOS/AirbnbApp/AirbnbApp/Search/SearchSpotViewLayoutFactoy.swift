//
//  SearchSpotViewLayoutFactoy.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

import UIKit.UICollectionView

struct SearchSpotViewLayoutFactoy: SectionLayoutCreator {

    static func makeSectionLayout(insetValue: CGFloat) -> NSCollectionLayoutSection {
        let item = NSCollectionLayoutItem(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(1),
                                                                             heightDimension: .fractionalHeight(0.1)))
        item.contentInsets = NSDirectionalEdgeInsets(top: insetValue,
                                                     leading: insetValue,
                                                     bottom: insetValue,
                                                     trailing: -insetValue)
        
        let group = NSCollectionLayoutGroup.vertical(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(1),
                                                                                          heightDimension: .fractionalHeight(1)),
                                                                                            subitems: [item])

        let section = NSCollectionLayoutSection(group: group)

        section.boundarySupplementaryItems = [NSCollectionLayoutBoundarySupplementaryItem(layoutSize: NSCollectionLayoutSize(
            widthDimension: .fractionalWidth(1),
            heightDimension: .estimated(50)),
            elementKind: MainHeaderView.ID,
            alignment: .topLeading)]

        return section
    }
}
