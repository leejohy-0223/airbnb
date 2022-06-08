//
//  MainViewDataSourceManager.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

import UIKit

struct MainViewDataSourceManager {
    private static var dataSource: UICollectionViewDiffableDataSource<MainViewSection, MainViewSectionData>?
    
    // regist Cell, configure Cell
    static func setDataSource(in collectionView: UICollectionView) {
        let heroCellRegistration = CollectioinViewRegistrator.createHeroCellRegestration()
        let nearSpotCellRegistration = CollectioinViewRegistrator.createSpotViewCellRegestration()
        let recommendResgistration = CollectioinViewRegistrator.createRecommendSpotCellRegestration()
        let sectionHeaderResigtration = CollectioinViewRegistrator.createHeaderRegistration()
        
        let dataSource: UICollectionViewDiffableDataSource<MainViewSection, MainViewSectionData>? =
            .init(collectionView: collectionView) { collectionView, indexPath, data in
                guard let section = MainViewSection(rawValue: indexPath.section) else { return  nil }
                
                switch section {
                case .hero:
                    return collectionView.dequeueConfiguredReusableCell(
                        using: heroCellRegistration,
                        for: indexPath,
                        item: HeroImage(image: data.image))
                case .nearSpot:
                    return collectionView.dequeueConfiguredReusableCell(
                        using: nearSpotCellRegistration,
                        for: indexPath,
                        item: NearSpot(image: data.image,
                                       spotName: data.spotName ?? "",
                                       distance: data.distance ?? 0))
                case .recommend:
                    return collectionView.dequeueConfiguredReusableCell(
                        using: recommendResgistration,
                        for: indexPath,
                        item: Recommend(image: data.image,
                                        name: data.spotName ?? ""))
                }
            }
        
        dataSource?.supplementaryViewProvider = { collectionView, _, indexPath in
            collectionView.dequeueConfiguredReusableSupplementary(using: sectionHeaderResigtration, for: indexPath)
        }
        self.dataSource = dataSource
            
    }
    
    // Configure SnapShot From MainViewInfo
    static func snapshot(data: MainViewInfo?) {
        guard let data = data else {  return  }
        
        let heroSectionData = MainViewSectionData.hero(image: data.heroImage)
        let nearSpotSectionData = data.NearSpot.map{ MainViewSectionData.nearSpot(spot: $0) }
        let recommendSectionData = data.recommend.map { MainViewSectionData.recommend(recommend: $0)}
        
        var heroImageSnapShot = NSDiffableDataSourceSectionSnapshot<MainViewSectionData>()
        heroImageSnapShot.append([heroSectionData])
        
        var nearSpotSnapShot = NSDiffableDataSourceSectionSnapshot<MainViewSectionData>()
        nearSpotSnapShot.append(nearSpotSectionData)
        
        var recommendSnapShot = NSDiffableDataSourceSectionSnapshot<MainViewSectionData>()
        recommendSnapShot.append(recommendSectionData)
        
        self.dataSource?.apply(heroImageSnapShot, to: .hero, animatingDifferences: true)
        self.dataSource?.apply(nearSpotSnapShot, to: .nearSpot, animatingDifferences: true)
        self.dataSource?.apply(recommendSnapShot, to: .recommend, animatingDifferences: true)
        
    }
}
