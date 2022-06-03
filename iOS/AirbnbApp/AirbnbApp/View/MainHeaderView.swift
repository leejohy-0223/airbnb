//
//  MainHeaderView.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/03.
//

import UIKit
import SnapKit

final class MainHeaderView: UICollectionReusableView {
    
    static let ID = "mainViewHeaderID"
    
    private var headerLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .left
        label.textColor = .label
        label.numberOfLines = 2
        label.font = .systemFont(ofSize: Constants.Label.mainHeaderViewLabelFontSize, weight: .bold)
        return label
    }()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUp()
    }
    
    @available(*, unavailable) required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func setLabel(text: String) {
        self.headerLabel.text = text
    }
    
    private func setUp() {
        self.addSubview(headerLabel)
        headerLabel.snp.makeConstraints {
            $0.leading.top.bottom.trailing.equalToSuperview().inset(16.0)
        }
    }
}
