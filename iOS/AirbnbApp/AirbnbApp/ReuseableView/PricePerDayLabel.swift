//
//  PriceLabel.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/27.
//

import UIKit

final class PricePerDayLabel: UILabel {
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
        
        self.attributedText = labelText
    }
}
