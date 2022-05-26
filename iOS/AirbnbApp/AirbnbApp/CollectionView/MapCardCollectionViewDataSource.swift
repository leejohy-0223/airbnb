//
//  MapCardCollectionViewDataSource.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/26.
//

import UIKit

final class MapCardCollectionViewDataSource: NSObject, UICollectionViewDataSource {
    
    private var houseInfo:[HouseInfo] = []
    private var buttonTappedDelegate: HeartButtonDelegate
    
    init(delegate: HeartButtonDelegate) {
        self.buttonTappedDelegate = delegate
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return houseInfo.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: MapViewCardCell.ID,for: indexPath) as? MapViewCardCell
              else { return UICollectionViewCell() }
        cell.delegate = buttonTappedDelegate
        configureCell(cell: cell, indexPath: indexPath)
        return cell
    }
    
    private func configureCell(cell: MapViewCardCell, indexPath: IndexPath) {
        
        cell.setCardID(index: indexPath.item)
        cell.setReviewLabel(rating: houseInfo[indexPath.item].detail.rating, reviewCount: houseInfo[indexPath.item].detail.reviewCount)
        cell.setImage(image: UIImage(systemName: "house")!)
        cell.setPrice(price: houseInfo[indexPath.item].price)
        cell.setHouseName(houseName: houseInfo[indexPath.item].name)
        cell.setHeartButton(isWish: houseInfo[indexPath.item].isWish)
    }
    
    func fetchHouseInfo(houseInfo: [HouseInfo]) {
        self.houseInfo = houseInfo
    }
    
    func changeIsWish(at cardIndex: Int?) {
        guard let cardIndex = cardIndex else { return }
        houseInfo[cardIndex].isWish = !houseInfo[cardIndex].isWish
    }
}
