//
//  NearSpotCell.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/02.
//

import UIKit
import SnapKit

final class NearSpotOverViewCell: UICollectionViewCell {

    static let ID = "NearSpotOverViewCell"
    
    private var imageView: UIImageView = {
        let imageView = UIImageView()
        imageView.backgroundColor = .blue
        return imageView
    }()
    
    private var InfoStackView: UIStackView = {
        let stackView = UIStackView()
        stackView.axis = .vertical
        stackView.alignment = .leading
        return stackView
    }()
    
    private var localLabel: UILabel = {
        let label = UILabel()
        label.textColor = .label
        return label
    }()
    
    private var distanceLabel: UILabel = {
        let label = UILabel()
        label.textColor = .secondaryLabel
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
    
    func configure(image: Data?, title: String?, distance: Int? = nil) {
        self.imageView.image = UIImage(data: image ?? Data())
        self.localLabel.text = title
        if let distance = distance {
            self.distanceLabel.text = "\(distance)"
        }
    }
    
    private func addViews() {
        [localLabel, distanceLabel].forEach{
            InfoStackView.addArrangedSubview($0)
        }
        
        [imageView, InfoStackView].forEach {
            self.addSubview($0)
        }
    }
    
    private func setUp() {
        imageView.snp.makeConstraints {
            $0.leading.top.bottom.equalToSuperview()
            $0.width.equalTo(64)
        }
        
        InfoStackView.snp.makeConstraints {
            $0.leading.equalTo(imageView.snp.trailing).offset(8.0)
            $0.centerY.equalTo(self.snp.centerY)
        }
    }
}
