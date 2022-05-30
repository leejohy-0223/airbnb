//
//  SearchResultViewController.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/26.
//


import UIKit
import SnapKit

final class SearchResultViewController: UIViewController {
    
    private let tabelView = UITableView(frame: .zero, style: .plain)
    private lazy var dataSource: SearchResultTableViewDataSource = SearchResultTableViewDataSource(delegate: self)
    private lazy var houseInfoManager: HouseInfoManager = HouseInfoManager(houseInfoBundle: self.houseInfoBundle)
    
    private var houseInfoBundle: [HouseInfo] = [
        HouseInfo(name: "킹왕짱 숙소", detail: Detail(rating: 4.45, reviewCount: 121), price: 101231, hostingBy: "김씨", latitude: 37.490765, longitude: 127.033433),
        HouseInfo(name: "킹짱 숙소", detail: Detail(rating: 4.35, reviewCount: 121), price: 1032131, hostingBy: "김씨", latitude: 37.480765, longitude: 127.032433),
        HouseInfo(name: "왕짱 숙소", detail: Detail(rating: 4.25, reviewCount: 121), price: 10141244, hostingBy: "김씨", latitude: 37.47065, longitude: 127.031433),
        HouseInfo(name: "한국 어딘가의 아주 근사한 숙소인데 이름이 조금 길어 근데 좋은데니까 한번 눌러", detail: Detail(rating: 4.15, reviewCount: 121), price: 10001, hostingBy: "김씨", latitude: 37.490765, longitude: 127.037433)
    ]
    
    private var mapButton: UIButton = {
        let button = UIButton()
        return button
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        addViews()
        setTableView()
        setMapButton()
    }
    
    private func addViews() {
        [tabelView, mapButton].forEach {
            self.view.addSubview($0)
        }
    }
    
    private func setTableView() {
        self.tabelView.register(ResultCardCell.self, forCellReuseIdentifier: ResultCardCell.ID)
        self.tabelView.separatorStyle = .none
        self.tabelView.rowHeight = UITableView.automaticDimension
        self.tabelView.estimatedRowHeight = 300
        
        self.dataSource.fetchHouseInfo(houseInfo: self.houseInfoManager.houseInfoBundle)
        tabelView.dataSource = self.dataSource
        
        tabelView.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
    }
    
    private func setMapButton() {
        
        let action = UIAction { [weak self] _  in
            guard let self = self else { return }
            let mapVC = MapViewController()
            mapVC.fetchHouseInfo(houseInfoManager: self.houseInfoManager)
            self.present(mapVC, animated: true)
        }
        
        var config = UIButton.Configuration.filled()
        
        var title = AttributedString(stringLiteral: " 지도")
        title.font = .systemFont(ofSize: Constants.Button.mapTitleFontSize, weight: .bold)
        config.attributedTitle = title

        config.image = UIImage(systemName: "map", withConfiguration:UIImage.SymbolConfiguration(weight: .bold))
        config.imagePlacement = .leading
        
        config.baseForegroundColor = .label
        
        mapButton.configuration = config
        mapButton.addAction(action, for: .touchUpInside)
        
        mapButton.snp.makeConstraints {
            $0.bottom.equalTo(self.view.safeAreaLayoutGuide).inset(32)
            $0.centerX.equalTo(self.view.snp.centerX)
            $0.width.equalTo(Constants.Button.mapButtonWidth)
        }
    }
}

extension SearchResultViewController: HeartButtonDelegate {
    func heartButtonIsTapped(_ cardIndex: Int?) {
        houseInfoManager.didChangeIsWish(cardIndex, completionHandler:  { houseInfoBundle in
            self.dataSource.fetchHouseInfo(houseInfo: houseInfoBundle)
        })
    }
}
