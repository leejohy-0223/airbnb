//
//  MapViewCardCell.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/25.
//

import UIKit
import SnapKit

final class MapViewCardCell: UICollectionViewCell {
    
    static let ID = "MapCell"
    private var cardIndex: Int?
    
    private lazy var houseImageView: HouseImageView = HouseImageView(frame: .zero)
    private lazy var reviewLabel: ReviewLabel = ReviewLabel(frame: .zero)
    private lazy var pricePerDayLabel: PricePerDayLabel = PricePerDayLabel(frame: .zero)
    private lazy var houseNameLabel: HouseNameLabel = HouseNameLabel(frame: .zero)
    lazy var heartButton: HeartButton = HeartButton(frame: .zero)
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        addViews()
        setUp()
    }
    
    @available(*, unavailable) required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func setReviewLabel(rating: Double, reviewCount: Int) {
        self.reviewLabel.setReviewLabel(rating: rating, reviewCount: reviewCount)
    }
    
    func setImage(image: UIImage) {
        self.houseImageView.setImage(image: image)
    }
    
    func setHouseName(numberOfLine: Int, fontSize: CGFloat, houseName: String) {
        self.houseNameLabel.setHouseNameLabel(numberOfLine: numberOfLine, fontSize: fontSize, houseName: houseName)
    }
    
    func setPrice(price: Int) {
        self.pricePerDayLabel.setPrice(price: price)
    }
    
    func setHeartButton(isWish: Bool) {
        heartButton.setHeartButton(isWish: isWish)
        heartButton.setCardIndex(index: self.cardIndex)
    }
    
    func setCardIndex(index: Int) {
        self.cardIndex = index
    }
    
    private func addViews() {
        [houseImageView, reviewLabel, heartButton, houseNameLabel,pricePerDayLabel].forEach {
            self.addSubview($0)
        }
    }
    
    private func setUp() {
        self.backgroundColor = .systemBackground
        let insetValue = 12.0
        
        self.houseImageView.snp.makeConstraints {
            $0.width.equalTo(self.frame.width / 3)
            $0.leading.height.bottom.equalToSuperview()
        }
        
        self.reviewLabel.snp.makeConstraints {
            $0.leading.equalTo(self.houseImageView.snp.trailing).offset(insetValue)
            $0.top.equalTo(self.snp.top).inset(insetValue)
        }
        
        self.heartButton.snp.makeConstraints {
            $0.top.trailing.equalToSuperview()
        }
        
        self.houseNameLabel.snp.makeConstraints {
            $0.leading.equalTo(reviewLabel.snp.leading)
            $0.top.equalTo(reviewLabel.snp.bottom).offset(insetValue)
            $0.trailing.equalToSuperview()
        }
        
        self.pricePerDayLabel.snp.makeConstraints {
            $0.leading.equalTo(houseNameLabel)
            $0.bottom.equalToSuperview().inset(insetValue)
        }
    }
}
