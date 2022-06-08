//
//  SearchResultController.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/06.
//

import UIKit
import SnapKit

final class SearchBarResultController: UIViewController {
    
    private lazy var tableView: UITableView = {
        let tableView = UITableView(frame: .zero, style: .plain)
        tableView.largeContentTitle = "근처 인기 여행지"
        tableView.backgroundView?.backgroundColor = .blue
        return tableView
    }()
    
    private var NearSpots = [NearSpot(name: "서울", distance: 4, image: "")]
    
    private var dataSource: UITableViewDiffableDataSource<Section, NearSpot>?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setTableView()
    }
    
    
    private func setTableView() {
        self.view.addSubview(tableView)
        
        tableView.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
    }
    
    
    enum Section: Hashable {
        case nearSpot
        case searchedSpot
    }
    
    struct NearSpot: Hashable {
        let name: String
        let distance: Int
        let image: String
    }
    
}

