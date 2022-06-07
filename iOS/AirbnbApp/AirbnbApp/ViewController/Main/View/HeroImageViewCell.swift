//
//  HeroImageViewCell.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/02.
//

import UIKit
import SnapKit

final class HeroImageViewCell: UICollectionViewCell {
    
    static let ID = "HeroImageViewCell"
    
    private let recommendImageView: HeroImageView = HeroImageView(frame: .zero)
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = .blue
        setUp()
    }
    
    @available(*, unavailable) required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func configure(image: Data?) {
        self.recommendImageView.image = UIImage(data: image ?? Data())
    }
    
    private func setUp() {
        self.addSubview(recommendImageView)
        
        recommendImageView.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
    }
    
}
