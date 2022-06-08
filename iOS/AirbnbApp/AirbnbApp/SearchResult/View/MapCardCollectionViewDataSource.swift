//
//  MapCardCollectionViewDataSource.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/26.
//

import UIKit

final class MapCardCollectionViewDataSource: NSObject, UICollectionViewDataSource {
    
    private var houseInfoBundle:[HouseInfo] = []
    private weak var buttonTappedDelegate: HeartButtonDelegate?
    
    init(delegate: HeartButtonDelegate) {
        self.buttonTappedDelegate = delegate
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return houseInfoBundle.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: MapViewCardCell.ID,for: indexPath) as? MapViewCardCell
              else { return UICollectionViewCell() }
        cell.heartButton.delegate = buttonTappedDelegate
        configureCell(cell: cell, indexPath: indexPath)
        return cell
    }
    
    private func configureCell(cell: MapViewCardCell, indexPath: IndexPath) {
        let houseInfo = houseInfoBundle[indexPath.item]
        cell.setCardIndex(index: indexPath.item)
        cell.setReviewLabel(rating: houseInfo.detailInfo.rate, reviewCount: houseInfo.detailInfo.commentCount)
        cell.setImage(image: UIImage(systemName: "house")!)
        cell.setPrice(price: houseInfo.price)
        cell.setHouseName(numberOfLine: 2, fontSize: Constants.Label.mapCardHouseNameFontSize, houseName: houseInfo.name)
        cell.setHeartButton(isWish: houseInfo.isWish)
    }
    
    func fetchHouseInfo(houseInfo: [HouseInfo]) {
        self.houseInfoBundle = houseInfo
    }
    
    func changeIsWish(at cardIndex: Int?) {
        guard let cardIndex = cardIndex else { return }
        houseInfoBundle[cardIndex].isWish = !houseInfoBundle[cardIndex].isWish
    }
}
