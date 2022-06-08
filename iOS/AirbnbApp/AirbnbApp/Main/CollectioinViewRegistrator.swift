//
//  CollectioinViewRegistrator.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//


import UIKit.UICollectionView

struct CollectioinViewRegistrator {
    private static let imageViewModel: MainViewImageViewModel = MainViewImageViewModel(repository:Repository
        .init(networkManager:NetworkManager(sessionManager: .default)))
    
    private static let MainViewHeaderViewModel: SectionHeaderViewModel = SectionHeaderViewModel()
    
    // header
    static func createHeaderRegistration() -> UICollectionView.SupplementaryRegistration<MainHeaderView> {
        UICollectionView.SupplementaryRegistration<MainHeaderView>(elementKind: MainHeaderView.ID) { header, _ , indexPath in
            guard let text = MainViewHeaderViewModel.getTitle(at: indexPath.section) else { return }
            header.setLabel(text: text)
        }
    }
    
    // hero
    static func createHeroCellRegestration() -> UICollectionView.CellRegistration<HeroImageViewCell, HeroImage> {
        UICollectionView.CellRegistration<HeroImageViewCell, HeroImage> { cell, _, image in
            imageViewModel.fetchImage(image: image.image , onCompleted: { data in
                cell.configure(image: data)
            })
        }
    }
    
    // NearSpot
    static func createSpotViewCellRegestration() -> UICollectionView.CellRegistration<NearSpotOverViewCell, NearSpot> {
        UICollectionView.CellRegistration<NearSpotOverViewCell, NearSpot> { cell, _, nearSpot in
            imageViewModel.fetchImage(image: nearSpot.image , onCompleted: { data in
                cell.configure(image: data, title: nearSpot.spotName, distance: nearSpot.distance)
            })
        }
    }
    
    // Recommend
    static func createRecommendSpotCellRegestration() -> UICollectionView.CellRegistration<RecommendCardCell, Recommend> {
        UICollectionView.CellRegistration<RecommendCardCell, Recommend> { cell, _, recommend in
            imageViewModel.fetchImage(image: recommend.image , onCompleted: { data in
                cell.configure(image: data, title: recommend.name)
            })
        }
    }
}

