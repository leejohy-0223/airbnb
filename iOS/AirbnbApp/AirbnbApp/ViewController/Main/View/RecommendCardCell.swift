//
//  RecommendCardCell.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/03.
//

import UIKit

final class RecommendCardCell: UICollectionViewCell {
    
    static let ID = "RecommendCardCell"
    
    private lazy var imageView: UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFit
        return imageView
    }()
    
    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 2
        label.font = .systemFont(ofSize: 32.0, weight: .bold)
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
    
    private func addViews() {
        [imageView, titleLabel].forEach {
            self.addSubview($0)
        }
    }
    
    func configure(image: Data?, title: String?) {
        self.imageView.image = UIImage(data: image ?? Data())
        self.titleLabel.text = title
    }
    
    private func setUp() {
        let insetValue = 8.0
        
        imageView.snp.makeConstraints {
            $0.leading.top.trailing.equalToSuperview()
            $0.height.equalTo(300)
        }
        
        titleLabel.snp.makeConstraints {
            $0.leading.trailing.equalToSuperview()
            $0.top.equalTo(imageView.snp.bottom).offset(insetValue)
        }
    }
}
