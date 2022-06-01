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
    
    private let houseInfoManager: HouseInfoRepository = HouseInfoRepository(
        networkManager: NetworkManager(sessionManager: .default))
    
    private lazy var mapButton: UIButton = {
        let button = UIButton()
        return button
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        fetchData()
        addViews()
        setTableView()
        setMapButton()
    }
    
    private func fetchData() {
        houseInfoManager.fetchHouseInfo(endpoint: EndPointCase.getHousesInfo.endpoint) { [weak self] (houseData: [HouseInfo]?) in
            guard let self = self else { return }
            self.dataSource.fetchHouseInfo(houseInfo: houseData ?? [])
        }
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
        
        tabelView.dataSource = self.dataSource
        
        tabelView.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
    }
    
    private func setMapButton() {
        
        let action = UIAction { [weak self] _  in
            guard let self = self else { return }
            let mapVC = MapViewController()
            mapVC.getHouseInfoManager(houseInfoManager: self.houseInfoManager)
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
