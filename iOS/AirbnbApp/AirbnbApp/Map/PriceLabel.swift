//
//  PriceLabel.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/24.
//

import UIKit

final class PriceLabel: UILabel {
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUp()
    }
    
    private func setUp() {
        self.layer.borderWidth = 1.0
        self.layer.cornerRadius = 10
        
        self.clipsToBounds = true
        self.textAlignment = .center
        
        self.minimumScaleFactor = 0.5
        self.adjustsFontSizeToFitWidth = true
        
        self.backgroundColor = .systemBackground
        self.frame = CGRect(origin: .zero,
                            size: CGSize(width: CGFloat(Constants.Pin.size.width),
                                         height: CGFloat(Constants.Pin.size.height)))
    }
    
    @available(*, unavailable) required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
