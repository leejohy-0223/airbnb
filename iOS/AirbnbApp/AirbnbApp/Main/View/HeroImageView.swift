//
//  HeroImageView.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/02.
//

import UIKit

final class HeroImageView: UIImageView {
    
    private let stackView: UIStackView = {
        let stackView = UIStackView()
        stackView.axis = .vertical
        stackView.alignment = .leading
        stackView.spacing = 16.0
        return stackView
    }()
    
    private let titleLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .left
        label.numberOfLines = 2
        label.font = .systemFont(ofSize: 32.0, weight: .bold)
        label.text = "슬기로운\n자연생활"
        return label
    }()
    
    private let subTitleLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .left
        label.numberOfLines = 2
        label.text = "에어비앤비가 엄선한\n위시리스트를 만나보세요."
        label.font = .systemFont(ofSize: 16.0, weight: .light)
        return label
    }()
    
    private let getIdeaButton: UIButton = {
        let insetValue = 8.0
        let button = UIButton()
        var config = UIButton.Configuration.plain()
        config.title = "여행 아이디어 얻기"
        config.contentInsets = NSDirectionalEdgeInsets(top: insetValue, leading: insetValue, bottom: insetValue, trailing: insetValue)
        config.baseForegroundColor = .label
        config.background.cornerRadius = 8.0
        config.background.strokeColor = .label
        button.configuration = config
        return button
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
        [titleLabel, subTitleLabel, getIdeaButton].forEach {
            self.stackView.addArrangedSubview($0)
        }
        self.addSubview(stackView)
    }
    
    private func setUp() {
        let insetValue = 16.0
        
        stackView.snp.makeConstraints {
            $0.leading.equalToSuperview().inset(insetValue)
            $0.top.equalToSuperview().inset(insetValue)
            $0.width.equalTo(titleLabel.snp.width)
        }
    }
}
