//
//  ResultCardCell.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/26.
//

import UIKit

final class ResultCardCell: UITableViewCell {
    
    static let ID = "ResultCardCell"
    private var cardIndex: Int?
    
    private lazy var houseInfoStackView: UIStackView = UIStackView(frame: .zero)
    private lazy var houseImageView: HouseImageView = HouseImageView(frame: .zero)
    private lazy var reviewLabel: ReviewLabel = ReviewLabel(frame: .zero)
    private lazy var pricePerDayLabel: PricePerDayLabel = PricePerDayLabel(frame: .zero)
    private lazy var houseNameLabel: HouseNameLabel = HouseNameLabel(frame: .zero)
    private lazy var totalPrice: UILabel = UILabel(frame: .zero)
    lazy var heartButton: HeartButton = HeartButton(frame: .zero)
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        self.backgroundColor = .systemBackground
        addViews()
        setImageView()
        setStackView()
        addHeartButton()
    }
    
    @available(*, unavailable) required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func addViews() {
        [houseImageView, houseInfoStackView].forEach {
            self.contentView.addSubview($0)
        }
    }
    
    private func addHeartButton() {
        self.contentView.addSubview(heartButton)
        
        self.heartButton.snp.makeConstraints {
            $0.trailing.equalToSuperview().inset(16.0)
            $0.top.equalToSuperview().inset(16.0)
        }
    }
    
    private func setImageView() {
        let insetValue = 16.0
        self.houseImageView.backgroundColor = . white
        self.houseImageView.layer.cornerRadius = insetValue / 2
        
        self.houseImageView.snp.makeConstraints {
            $0.top.leading.trailing.equalToSuperview().inset(insetValue)
            $0.bottom.lessThanOrEqualToSuperview()
            $0.height.equalTo(Constants.ImageViewSize.SearchResultImage)
        }
    }
    
    private func setStackView() {
        self.houseInfoStackView.axis = .vertical

        [reviewLabel, houseNameLabel, pricePerDayLabel, totalPrice].forEach {
            houseInfoStackView.addArrangedSubview($0)
        }

        houseInfoStackView.snp.makeConstraints{
            $0.top.equalTo(houseImageView.snp.bottom).offset(8.0)
            $0.bottom.lessThanOrEqualToSuperview()
            $0.leading.trailing.equalTo(houseImageView)
        }
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
    
    func setTotalPrice(price: Int) {
        self.totalPrice.textAlignment = .left
        self.totalPrice.textColor = .secondaryLabel
        
        let underLineAttribute = [NSAttributedString.Key.underlineStyle: NSUnderlineStyle.single.rawValue]
        let text = NSAttributedString(string: "총액 \(PriceConvertor.toString(from: price))", attributes: underLineAttribute)
        
        self.totalPrice.attributedText = text
    }
}
