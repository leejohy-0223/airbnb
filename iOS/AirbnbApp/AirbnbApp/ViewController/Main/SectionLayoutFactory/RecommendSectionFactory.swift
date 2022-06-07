//
//  RecommendSectionFactory.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/06.
//

import UIKit.UICollectionView

final class RecommendSectionFactory: MainViewSectionCreator {
    
    static func makeSectionLayout(insetValue: CGFloat) -> NSCollectionLayoutSection {
        let item = NSCollectionLayoutItem(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(1),
                                                                             heightDimension: .fractionalHeight(1)))
        item.contentInsets = NSDirectionalEdgeInsets(top: 0,
                                                     leading: insetValue,
                                                     bottom: 0,
                                                     trailing: 0)
        
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(0.7),
                                                                                        heightDimension: .estimated(400)),
                                                                                        subitems: [item])
        
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
