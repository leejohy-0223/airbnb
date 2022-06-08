//
//  ResultHeaderView.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/31.
//

import UIKit
import SnapKit

final class ResultHeaderView: UITableViewHeaderFooterView {
    
    static let ID = "ResultHeaderView"
    
    private lazy var inputValueLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .left
        return label
    }()
    
    private lazy var houseCountLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .left
        label.font = .systemFont(ofSize: Constants.Label.houseCountLabelFontSize, weight: .bold)
        return label
    }()
    
    
    override init(reuseIdentifier: String?) {
        super.init(reuseIdentifier: reuseIdentifier)
        addViews()
        setUp()
    }
    
    @available(*, unavailable) required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    
    private func addViews() {
        [inputValueLabel, houseCountLabel].forEach {
            self.addSubview($0)
        }
    }
    
    private func setUp() {
        let insetValue = 12.0
        
        inputValueLabel.snp.makeConstraints {
            $0.top.leading.equalToSuperview().inset(insetValue)
        }
        
        houseCountLabel.snp.makeConstraints {
            $0.leading.equalToSuperview().inset(insetValue)
            $0.top.equalTo(inputValueLabel.snp.bottom)
            $0.bottom.equalToSuperview()
        }
        
    }
    
    
    
    func setInputValueLabel(location: String?, startDate: Int?, endDate: Int?, peopleCount: Int?) {
        var text = ""
        if let location = location {
            text += "\(location) •"
        }
        
        if let startDate = startDate {
            text += "\(startDate) 월 1일 - \(endDate ?? 1)월 1일"
        }
        
        if let peopleCount = peopleCount {
            text += "게스트 \(peopleCount)명"
        }
        
        self.inputValueLabel.text = text
    }
    
    func setHouseCountLabel(houseCount: Int) {
        if houseCount > 300 {
            self.houseCountLabel.text = "300개 이상의 숙소"
        } else {
            self.houseCountLabel.text = "\(houseCount)개의 숙소"
        }
    }
}
