//
//  SearchResultTableViewDataSource.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/27.
//

import UIKit

final class SearchResultTableViewDataSource: NSObject, UITableViewDataSource {
    private var houseInfoBundle:[HouseInfo] = []
    private weak var buttonTappedDelegate: HeartButtonDelegate?
    
    init(delegate: HeartButtonDelegate) {
        self.buttonTappedDelegate = delegate
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return houseInfoBundle.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: ResultCardCell.ID,
                                                       for: indexPath) as? ResultCardCell else { return  UITableViewCell() }
        cell.heartButton.delegate = buttonTappedDelegate
        configureCell(cell: cell, indexPath: indexPath)
        return cell
    }
    
    private func configureCell(cell: ResultCardCell, indexPath: IndexPath) {
        let houseInfo = houseInfoBundle[indexPath.row]
        cell.setCardIndex(index: indexPath.row)
        cell.setReviewLabel(rating: houseInfo.detailInfo.rate, reviewCount: houseInfo.detailInfo.commentCount)
        cell.setImage(image: UIImage(systemName: "house")!)
        cell.setPrice(price: houseInfo.price)
        cell.setHouseName(numberOfLine: 1, fontSize: Constants.Label.mapCardHouseNameFontSize, houseName: houseInfo.name)
        cell.setHeartButton(isWish: houseInfo.isWish)
        cell.setTotalPrice(price: 140000)
    }
    
    func fetchHouseInfoBundle(houseInfoBundle: [HouseInfo]) {
        self.houseInfoBundle = houseInfoBundle
    }
    
    func changeIsWish(at cardIndex: Int?) {
        guard let cardIndex = cardIndex else { return }
        if houseInfoBundle.checkIsSafeIndex(index: cardIndex) {
            houseInfoBundle[cardIndex].isWish = !houseInfoBundle[cardIndex].isWish
        }
    }
}
