//
//  SearchSpotViewSectionManager.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

import MapKit
import SwiftUI

struct SearchSpotViewSectionManager {
    private static var dataSource:UICollectionViewDiffableDataSource<SearchSpotViewSection, SearchedSpot>?
    
    // regist Cell, configure Cell
    static func setDataSource(in collectionView: UICollectionView) {
        let spotCellRegistration = CollectioinViewRegistrator.createSpotViewCellRegestration()
        let sectionHeaderResigtration = CollectioinViewRegistrator.createHeaderRegistration()
        
        let dataSource: UICollectionViewDiffableDataSource<SearchSpotViewSection, SearchedSpot>? =
            .init(collectionView: collectionView) { collectionView, indexPath, data in
                guard let section = SearchSpotViewSection(rawValue: indexPath.section) else { return nil }
                switch section {
                case .searchResult:
                    return collectionView.dequeueConfiguredReusableCell(using: spotCellRegistration,
                                                                        for: indexPath,
                                                                        item: SearchedSpot(spotName: data.spotName ))
                }
            }
        dataSource?.supplementaryViewProvider = { collectionView, _, indexPath in
            collectionView.dequeueConfiguredReusableSupplementary(using: sectionHeaderResigtration, for: indexPath)
        }
            
        self.dataSource = dataSource
    }
    
    // Configure SnapShot
    static func snapshot(data: [MKLocalSearchCompletion]?) {
        guard let searchedViewdata = data else { return }
        
        let result = searchedViewdata.map {
            SearchedSpot(spotName: $0.title)
        }
        
        var snapShot = NSDiffableDataSourceSnapshot<SearchSpotViewSection, SearchedSpot>()
        
        snapShot.appendSections([.searchResult])
        snapShot.appendItems(result)

        self.dataSource?.apply(snapShot, animatingDifferences: true)
    }
}

