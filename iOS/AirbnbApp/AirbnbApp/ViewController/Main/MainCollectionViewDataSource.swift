//
//  MainCollectionViewDataSource.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/03.
//

import UIKit.UICollectionView

final class MainCollectionViewDataSource: NSObject, UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView,
                        viewForSupplementaryElementOfKind kind: String,
                        at indexPath: IndexPath) -> UICollectionReusableView {
        guard let header = collectionView.dequeueReusableSupplementaryView(ofKind: kind,
                                                                           withReuseIdentifier: MainHeaderView.ID,
                                                                           for: indexPath) as? MainHeaderView
        else { return UICollectionReusableView() }
        
        switch indexPath.section {
        case 1:
            header.setLabel(text: "가까운 여행지 둘러보기")
            return header
        case 2:
            header.setLabel(text: "어디에서나, 여행은 살아보는거야!")
            return header
        default:
            return header
        }
    }
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 3
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        switch section {
        case 0:
            return 1
        case 1:
            return 10
        case 2:
            return 5
        default:
            return 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        switch indexPath.section {
        case 0:
            guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: HeroImageViewCell.ID, for: indexPath) as?
                    HeroImageViewCell else { return UICollectionViewCell() }
            return cell
        case 1:
            guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: NearSpotOverViewCell.ID, for: indexPath) as?
                    NearSpotOverViewCell else { return UICollectionViewCell() }
            
            return cell
        case 2:
            guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: RecommendCardCell.ID, for: indexPath) as?
                    RecommendCardCell else { return UICollectionViewCell() }
            cell.setLabel(text: "SSuk So")
            return cell
        default:
            return UICollectionViewCell()
        }
    }
}
