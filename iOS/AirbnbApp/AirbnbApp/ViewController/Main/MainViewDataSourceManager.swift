//
//  MainViewDataSourceManager.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

import UIKit

struct MainViewDataSourceManager {
    private static var dataSource:UICollectionViewDiffableDataSource<Section, SectionDataSource>?
    
    static func setDataSource(in collectionView: UICollectionView) {
        let heroCellRegistration = MainViewRegistrator.createHeroCellRegestration()
        let nearSpotCellRegistration = MainViewRegistrator.createNearSpotCellRegestration()
        let recommendResgistration = MainViewRegistrator.createRecommendSpotCellRegestration()
        let sectionHeaderResigtration = MainViewRegistrator.createHeaderRegistration()
        
        let dataSource: UICollectionViewDiffableDataSource<Section, SectionDataSource>? =
            .init(collectionView: collectionView) { collectionView, indexPath, data in
                guard let section = Section(rawValue: indexPath.section) else { return  nil }
                
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
                                        name: data.spotName ?? "")
                )
            }
        }
        
        dataSource?.supplementaryViewProvider = { collectionView, _, indexPath in
            collectionView.dequeueConfiguredReusableSupplementary(using: sectionHeaderResigtration, for: indexPath)
        }
        self.dataSource = dataSource
    }
    
    
    static func snapshot(data: [SectionDataSource]) {
        
        var heroImages = [SectionDataSource]()
        var nearSpots = [SectionDataSource]()
        var recommends = [SectionDataSource]()
        
        data.forEach { (data: SectionDataSource) in
            switch data {
            case .hero:
                heroImages.append(data)
            case .nearSpot:
                nearSpots.append(data)
            case .recommend:
                recommends.append(data)
            }
        }
        
        var heroImageSnapShot = NSDiffableDataSourceSectionSnapshot<SectionDataSource>()
        heroImageSnapShot.append(heroImages)
            
        var nearSpotSnapShot = NSDiffableDataSourceSectionSnapshot<SectionDataSource>()
        nearSpotSnapShot.append(nearSpots)
        
        var recommendSnapShot = NSDiffableDataSourceSectionSnapshot<SectionDataSource>()
        recommendSnapShot.append(recommends)
        
        self.dataSource?.apply(heroImageSnapShot, to: .hero, animatingDifferences: true)
        self.dataSource?.apply(nearSpotSnapShot, to: .nearSpot, animatingDifferences: true)
        self.dataSource?.apply(recommendSnapShot, to: .recommend, animatingDifferences: true)
    }
    
}
