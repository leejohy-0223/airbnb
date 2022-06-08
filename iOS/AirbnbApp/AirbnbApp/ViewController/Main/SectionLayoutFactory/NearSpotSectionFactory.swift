//
//  NearSpotSectionFactory.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/06.
//

import UIKit.UICollectionView

final class NearSpotSectionFactory: MainViewSectionCreator {
    
    static func makeSectionLayout(insetValue: CGFloat) -> NSCollectionLayoutSection {
        let item = NSCollectionLayoutItem(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(0.8),
                                                                             heightDimension: .fractionalHeight(0.2)))
        item.contentInsets = NSDirectionalEdgeInsets(top: insetValue,
                                                     leading: insetValue,
                                                     bottom: insetValue,
                                                     trailing: -insetValue)

        let group = NSCollectionLayoutGroup.vertical(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(0.7),
                                                                                        heightDimension: .fractionalHeight(0.25)),
                                                                                        subitem: item, count: 2)

        let section = NSCollectionLayoutSection(group: group)
        section.orthogonalScrollingBehavior = .continuous

        section.boundarySupplementaryItems = [NSCollectionLayoutBoundarySupplementaryItem(layoutSize: NSCollectionLayoutSize(
            widthDimension: .fractionalWidth(1),
            heightDimension: .estimated(50)),
            elementKind: MainHeaderView.ID,
            alignment: .topLeading)]

        return section
    }
}
