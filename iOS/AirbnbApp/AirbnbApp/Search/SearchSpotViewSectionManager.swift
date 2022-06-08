//
//  SearchSpotViewSectionManager.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

import UIKit
import MapKit

typealias SearchSpotResult = NearSpot

struct SearchSpotViewSectionManager {
    private static var dataSource:UICollectionViewDiffableDataSource<SearchSpotViewSection, MKLocalSearchCompletion>?
    
    // regist Cell, configure Cell
    static func setDataSource(in collectionView: UICollectionView) {
        let nearSpotCellRegistration = CollectioinViewRegistrator.createSpotViewCellRegestration()
        let sectionHeaderResigtration = CollectioinViewRegistrator.createHeaderRegistration()

        let dataSource: UICollectionViewDiffableDataSource<SearchSpotViewSection, MKLocalSearchCompletion>? =
            .init(collectionView: collectionView) { collectionView, indexPath, data in
                guard let section = SearchSpotViewSection(rawValue: indexPath.section) else { return nil }
                switch section {
                case .searchResult:
                    return collectionView.dequeueConfiguredReusableCell(using: nearSpotCellRegistration,
                                                                        for: indexPath,
                                                                        item:SearchSpotResult(spotName: data.title))
                }
            }
        dataSource?.supplementaryViewProvider = { collectionView, _, indexPath in
            collectionView.dequeueConfiguredReusableSupplementary(using: sectionHeaderResigtration, for: indexPath)
        }

        self.dataSource = dataSource
    }

    // Configure SnapShot
    static func snapshot(data: [MKLocalSearchCompletion]?) {
        guard let searchViewdata = data else { return }
        
        var snapShot = NSDiffableDataSourceSnapshot<SearchSpotViewSection, MKLocalSearchCompletion>()
        
        snapShot.appendSections([.searchResult])
        snapShot.appendItems(searchViewdata)

        self.dataSource?.apply(snapShot, animatingDifferences: true)
    }
}

