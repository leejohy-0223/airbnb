//
//  MainViewCellRegistration.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//


import UIKit.UICollectionView

struct MainViewRegistrator {
    private static let imageViewManager: MainViewImageViewManager = MainViewImageViewManager(repository: Repository.init(networkManager: NetworkManager(sessionManager: .default)))
    
    private static let headerViewModel: SectionHeaderViewModel = SectionHeaderViewModel()
    
    static func createHeaderRegistration() -> UICollectionView.SupplementaryRegistration<MainHeaderView> {
        UICollectionView.SupplementaryRegistration<MainHeaderView>(elementKind: MainHeaderView.ID) { header, _ , indexPath in
            guard let text = headerViewModel.getTitle(at: indexPath.section) else { return }
            header.setLabel(text: text)
        }
    }
    
    static func createHeroCellRegestration() -> UICollectionView.CellRegistration<HeroImageViewCell, HeroImage> {
        UICollectionView.CellRegistration<HeroImageViewCell, HeroImage> { cell, _, image in
            imageViewManager.fetchImage(image: image.image , onCompleted: { data in
                cell.configure(image: data)
            })
        }
    }
    
    static func createNearSpotCellRegestration() -> UICollectionView.CellRegistration<NearSpotOverViewCell, NearSpot> {
        UICollectionView.CellRegistration<NearSpotOverViewCell, NearSpot> { cell, _, nearSpot in
            imageViewManager.fetchImage(image: nearSpot.image , onCompleted: { data in
                cell.configure(image: data, title: nearSpot.spotName, distance: nearSpot.distance)
            })
        }
    }
    
    static func createRecommendSpotCellRegestration() -> UICollectionView.CellRegistration<RecommendCardCell, Recommend> {
        UICollectionView.CellRegistration<RecommendCardCell, Recommend> { cell, _, recommend in
            imageViewManager.fetchImage(image: recommend.image , onCompleted: { data in
                cell.configure(image: data, title: recommend.name)
            })
        }
    }
}

