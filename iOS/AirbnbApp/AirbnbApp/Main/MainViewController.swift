//
//  MainViewController.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/02.
//

import UIKit
import SnapKit

final class MainViewController: UIViewController {
    
    private lazy var collectionView: UICollectionView = {
        guard let layout = self.createLayout() else { return UICollectionView() }
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: layout)
        return collectionView
    }()
    
    private var searchVC: UISearchController?
    private var searchSpotViewController = SearchSpotViewController()
    
    private var sectionLayoutFactories: [MainViewSection: SectionLayoutCreator.Type] = [.hero: HeroImageSectionFactory.self,
                                                                                        .nearSpot: NearSpotSectionFactory.self,
                                                                                        .recommend: RecommendSectionFactory.self]

    private var sectionHeaderViewModel: SectionHeaderViewModel = SectionHeaderViewModel()
    
    private var sectionViewModel: MainViewSectionViewModel = MainViewSectionViewModel(repository: Repository(networkManager: NetworkManager(sessionManager: .default)))
    
    override func viewDidLoad() {
        super.viewDidLoad()
        addViews()
        setLayouts()
        setUpDataSource()
        setSearchViewController()
        setNavigationController()
    }
    
    private func addViews() {
        [collectionView].forEach {
            self.view.addSubview($0)
        }
    }
    
    private func setNavigationController() {
        self.navigationItem.searchController = searchVC
        self.navigationController?.hidesBarsOnSwipe = true
    }
    
    private func setSearchViewController() {
        self.searchVC = UISearchController(searchResultsController: self.searchSpotViewController)
        self.searchVC?.searchResultsUpdater = searchSpotViewController
        self.searchVC?.searchBar.placeholder = "어디로 여행가세요?"
        self.searchVC?.searchBar.showsCancelButton = false
        self.searchVC?.obscuresBackgroundDuringPresentation = true
        searchVC?.delegate = self
    }
    
    private func setLayouts() {
        self.collectionView.snp.makeConstraints{
            $0.top.bottom.leading.trailing.equalToSuperview()
        }
    }
    
    private func createLayout() -> UICollectionViewCompositionalLayout? {
        return UICollectionViewCompositionalLayout { sectionNumber, _ in
            let insetValue = 16.0
            guard let section = MainViewSection(rawValue: sectionNumber) else { return nil }
            return self.sectionLayoutFactories[section]?.makeSectionLayout(insetValue: insetValue)
        }
    }
    
    private func setUpDataSource() {
        MainViewDataSourceManager.setDataSource(in: collectionView)
        
        // MainView의 ViewModel 설정
        self.sectionViewModel.fetchMockData { data in
            MainViewDataSourceManager.snapshot(data: data)
        }
    }
}


extension MainViewController: UISearchControllerDelegate {
    
}
