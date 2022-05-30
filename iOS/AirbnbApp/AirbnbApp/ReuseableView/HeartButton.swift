//
//  HeartButton.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/27.
//

import UIKit

final class HeartButton: UIButton {
    
    weak var delegate: HeartButtonDelegate?
    private var cardIndex: Int?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.configuration = UIButton.Configuration.plain()
        self.tintColor = .secondaryLabel
        self.addTarget(self, action: #selector(changeImage), for: .touchUpInside)
    }
    
    @available(*, unavailable) required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func setCardIndex(index: Int?) {
        self.cardIndex = index
    }
    
    func setHeartButton(isWish: Bool) {
        if isWish {
            self.configuration?.image = UIImage(systemName: "heart.fill")
            self.configuration?.baseForegroundColor = .red
        } else {
            self.configuration?.image = UIImage(systemName: "heart")
            self.configuration?.baseForegroundColor = .secondaryLabel
        }
    }
    
    @objc private func changeImage() {
        if self.configuration?.image == UIImage(systemName: "heart") {
            self.configuration?.image = UIImage(systemName: "heart.fill")
            self.configuration?.baseForegroundColor = .red
        } else {
            self.configuration?.image = UIImage(systemName: "heart")
            self.configuration?.baseForegroundColor = .secondaryLabel
        }
        delegate?.heartButtonIsTapped(cardIndex)
    }
}
