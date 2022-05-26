//
//  PriceAnnotationView.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/24.
//

import Foundation
import MapKit

final class PriceAnnotationView: MKAnnotationView {
    private let priceLabel = PriceLabel()
    
    override init(annotation: MKAnnotation?, reuseIdentifier: String?) {
        super.init(annotation: annotation, reuseIdentifier: reuseIdentifier)
        setUp()
    }
    
    @available(*, unavailable) required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setUp() {
        self.backgroundColor = .systemBackground
        self.layer.cornerRadius = 8.0
        self.frame = priceLabel.frame   //Touch를 인식하기 위해 설정
        self.addSubview(priceLabel)
        
        self.canShowCallout = true
    }
    
    func setPrice(price: Int) {
        let pricetext = PriceConvertor.toString(from: price)
        self.priceLabel.text = pricetext
    }
    
}
