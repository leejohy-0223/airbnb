//
//  SearchResultViewController.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/26.
//


import UIKit
import SnapKit
import Alamofire

final class SearchResultViewController: UIViewController {
    
    private let tabelView = UITableView(frame: .zero, style: .grouped)
    private lazy var dataSource: SearchResultTableViewDataSource = SearchResultTableViewDataSource(delegate: self)
    
    private var houseInfoRepository: HouseInfoRepository = HouseInfoRepository(networkManager: NetworkManager(sessionManager: .default))
    
    private lazy var mapButton: UIButton = {
        let button = UIButton()
        return button
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setMockProcotol()
        fetchData()
        addViews()
        setTableView()
        setMapButton()
    }
    
    private func setMockProcotol() {
        let bundle = Bundle(for: Self.self)
        
        guard let mockDataURL = bundle.url(forResource: "MockHouseInfoData", withExtension: "json"),
              let mockData = try? Data(contentsOf: mockDataURL) else { return }
        
        // mockURL
        let mockEndpoint = EndPointCase.getHousesInfo.endpoint
        guard let mockURL = try? mockEndpoint.getURL().asURL() else { return }

        // Mock loadingHander 설정
        URLMockProtocol.loadingHandler = { request in
            let response = HTTPURLResponse(
                url: mockURL,
                statusCode: 200,
                httpVersion: nil,
                headerFields: nil)!
            
            if request.url == mockURL {
                return (response, mockData, nil)
            } else {
                return (response, nil, nil)
            }
        }
        
        // Mock URLProtocol 주입
        let config = URLSessionConfiguration.ephemeral
        config.protocolClasses = [URLMockProtocol.self]
        
        houseInfoRepository = HouseInfoRepository(networkManager: NetworkManager(sessionManager: Session(configuration: config)))
    }
    
    private func fetchData() {
        houseInfoRepository.fetchHouseInfo(endpoint: EndPointCase.getHousesInfo.endpoint) { [weak self] (houseData: [HouseInfo]?) in
            guard let self = self else { return }
            self.dataSource.fetchHouseInfo(houseInfo: houseData ?? [])
            self.tabelView.reloadData()
        }
    }
    
    private func addViews() {
        [tabelView, mapButton].forEach {
            self.view.addSubview($0)
        }
    }
    
    private func setTableView() {
        self.tabelView.register(ResultCardCell.self, forCellReuseIdentifier: ResultCardCell.ID)
        self.tabelView.register(ResultHeaderView.self, forHeaderFooterViewReuseIdentifier: ResultHeaderView.ID)
        self.tabelView.separatorStyle = .none
        self.tabelView.rowHeight = UITableView.automaticDimension
        self.tabelView.estimatedRowHeight = 300
        
        tabelView.dataSource = self.dataSource
        tabelView.delegate = self
        
        tabelView.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
    }
    
    private func setMapButton() {
        
        let action = UIAction { [weak self] _  in
            guard let self = self else { return }
            let mapVC = MapViewController()
            mapVC.getHouseInfoManager(houseInfoManager: self.houseInfoRepository)
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
        houseInfoRepository.didChangeIsWish(cardIndex, completionHandler:  { houseInfoBundle in
            self.dataSource.fetchHouseInfo(houseInfo: houseInfoBundle)
        })
    }
}


extension SearchResultViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        guard let headerView = tableView.dequeueReusableHeaderFooterView(withIdentifier: ResultHeaderView.ID)
                as? ResultHeaderView else { return nil }
        headerView.setHouseCountLabel(houseCount: houseInfoRepository.houseInfoBundle.count)
        headerView.setInputValueLabel(location: "양재", startDate: 5, endDate: 5, peopleCount: 3)
        return headerView
    }
}
