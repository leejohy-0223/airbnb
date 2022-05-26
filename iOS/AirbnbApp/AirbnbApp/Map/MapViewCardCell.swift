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
    
    weak var delegate: HeartButtonDelegate?
    
    private lazy var imageView: UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFit
        return imageView
    }()
    
    private lazy var reviewLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .left
        return label
    }()
    
    private lazy var heartButton: UIButton = {
        let button = UIButton()
        button.configuration = UIButton.Configuration.plain()
        button.tintColor = .secondaryLabel
        button.addTarget(self, action: #selector(changeImage), for: .touchUpInside)
        return button
    }()
    
    private lazy var houseNameLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 2
        label.lineBreakMode = .byTruncatingTail
        label.font = .systemFont(ofSize: Constants.Label.mapCardHouseNameFontSize)
        return label
    }()
    
    private lazy var pricePerDayLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .left
        return label
    }()
    
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        addViews()
        setUp()
    }
    
    @available(*, unavailable) required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    // Text별 색상과 속성을 다르게 줌.
    func setReviewLabel(rating: Double, reviewCount: Int) {
        let labelText = NSMutableAttributedString()
        
        // 별표
        guard let starImage = UIImage(systemName: "star.fill") else { return }
        let redStar = starImage.withTintColor(.red, renderingMode: .alwaysTemplate)
        let imageAttachment = NSTextAttachment(image: redStar)
        
        // 별점
        let ratingText = NSMutableAttributedString()
            .setting(string: "\(rating)  ",
                     fontSize: Constants.Label.mapCardRatingFontSize,
                     weight: .regular,
                     color: .label)
        
        //후기 갯수
        let reviewCountTest = NSMutableAttributedString()
            .setting(string: "후기 (\(reviewCount)개)",
                     fontSize: Constants.Label.mapCardReviewCountFontSize,
                     weight: .light,
                     color: .secondaryLabel)
        
        labelText.append(NSAttributedString(attachment:imageAttachment))
        labelText.append(ratingText)
        labelText.append(reviewCountTest)
        
        self.reviewLabel.attributedText = labelText
    }
    
    func setImage(image: UIImage) {
        self.imageView.image = image
    }
    
    func setHouseName(houseName: String) {
        self.houseNameLabel.text = houseName
    }
    
    func setPrice(price: Int) {
        let labelText = NSMutableAttributedString()
        
        let pricePerDayText = NSMutableAttributedString()
            .setting(string: PriceConvertor.toString(from: price),
                     fontSize: Constants.Label.mapCardPriceFontSize,
                     weight: .bold,
                     color: .label)
        
        let perDayText = NSMutableAttributedString()
            .setting(string: " / 박",
                     fontSize: Constants.Label.mapCardPriceFontSize,
                     weight: .light,
                     color: .secondaryLabel)
        
        labelText.append(pricePerDayText)
        labelText.append(perDayText)
        
        self.pricePerDayLabel.attributedText = labelText
    }
    
    func setHeartButton(isWish: Bool) {
        if isWish {
            heartButton.configuration?.image = UIImage(systemName: "heart.fill")
            heartButton.configuration?.baseForegroundColor = .red
        } else {
            heartButton.configuration?.image = UIImage(systemName: "heart")
            heartButton.configuration?.baseForegroundColor = .secondaryLabel
        }
    }
    
    func setCardID(index: Int) {
        self.cardIndex = index
    }
    
    private func addViews() {
        [imageView, reviewLabel, heartButton, houseNameLabel,pricePerDayLabel].forEach {
            self.addSubview($0)
        }
    }
    
    private func setUp() {
        self.backgroundColor = .white
        let insetValue = 12.0
        
        self.imageView.snp.makeConstraints {
            $0.width.equalTo(self.frame.width / 3)
            $0.leading.height.bottom.equalToSuperview()
        }
        
        self.reviewLabel.snp.makeConstraints {
            $0.leading.equalTo(self.imageView.snp.trailing).offset(insetValue)
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
    
    @objc private func changeImage() {
        if heartButton.configuration?.image == UIImage(systemName: "heart") {
            heartButton.configuration?.image = UIImage(systemName: "heart.fill")
            heartButton.configuration?.baseForegroundColor = .red
            delegate?.hearButtonIsTapped(cardIndex)
        } else {
            heartButton.configuration?.image = UIImage(systemName: "heart")
            heartButton.configuration?.baseForegroundColor = .secondaryLabel
            delegate?.hearButtonIsTapped(cardIndex)
        }
    }
}
