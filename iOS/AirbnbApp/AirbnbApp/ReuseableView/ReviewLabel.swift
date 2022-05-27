//
//  ReviewLabel.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/27.
//

import UIKit

final class ReviewLabel: UILabel {
    
    // Text별 색상과 속성을 다르게 줌.
    func setReviewLabel(rating: Double, reviewCount: Int) {
        self.textAlignment = .left
        
        let labelText = NSMutableAttributedString()
        // 별표
        guard let starImage = UIImage(systemName: "star.fill") else { return }
        let redStar = starImage.withTintColor(.systemRed, renderingMode: .alwaysTemplate)
        let imageAttachment = NSTextAttachment(image: redStar)
        
        // 별점
        let ratingText = NSMutableAttributedString()
            .setting(string: "\(rating)  ",
                     fontSize: Constants.Label.mapCardRatingFontSize,
                     weight: .regular,
                     color: .label)
        
        //후기 갯수
        let reviewCountText = NSMutableAttributedString()
            .setting(string: "후기 (\(reviewCount)개)",
                     fontSize: Constants.Label.mapCardReviewCountFontSize,
                     weight: .light,
                     color: .systemGray)
        
        labelText.append(NSAttributedString(attachment:imageAttachment))
        labelText.append(ratingText)
        labelText.append(reviewCountText)
        
        self.attributedText = labelText
    }
    
}
