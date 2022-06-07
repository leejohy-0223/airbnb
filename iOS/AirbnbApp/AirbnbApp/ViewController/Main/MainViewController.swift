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
    private var sectionLayoutFactories: [Section: MainViewSectionCreator.Type] = [.hero: HeroImageSectionFactory.self,
                                                                                  .nearSpot: NearSpotSectionFactory.self,
                                                                                  .recommend: RecommendSectionFactory.self]
        
    private var dataSource: UICollectionViewDiffableDataSource<Section, SectionDataSource>?

    private var sectionHeaderViewModel: SectionHeaderViewModel = SectionHeaderViewModel()
    private var imageViewManager: MainImageViewManager = MainImageViewManager(repository: Repository(networkManager: NetworkManager(sessionManager: .default)))
    
    private var mockData: [SectionDataSource] = [SectionDataSource.hero(image: HeroImage(image: "https://w7.pngwing.com/pngs/332/451/png-transparent-pepe-the-frog-pepe-4chan-television-face-leaf.png")),
                                                 SectionDataSource.recommend(recommend: Recommend(image: "https://w7.pngwing.com/pngs/332/451/png-transparent-pepe-the-frog-pepe-4chan-television-face-leaf.png", name: "Good House")),
                                                 SectionDataSource.nearSpot(spot: NearSpot(image: "https://w7.pngwing.com/pngs/332/451/png-transparent-pepe-the-frog-pepe-4chan-television-face-leaf.png", spotName: "Come~", distance: 30)),                                                 SectionDataSource.recommend(recommend: Recommend(image: "https://w7.pngwing.com/pngs/332/451/png-transparent-pepe-the-frog-pepe-4chan-television-face-leaf.png", name: "Come on")),                                                 SectionDataSource.nearSpot(spot: NearSpot(image: "https://w7.pngwing.com/pngs/332/451/png-transparent-pepe-the-frog-pepe-4chan-television-face-leaf.png", spotName: "좋지 않은 숙소", distance: 30))
    ]
    
    
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
        searchVC = UISearchController(searchResultsController: SearchBarResultController())
        searchVC?.searchBar.placeholder = "어디로 여행가세요?"
        searchVC?.searchBar.showsCancelButton = false
//        searchVC?.delegate = self
    }

    
    private func setLayouts() {
        self.collectionView.snp.makeConstraints{
            $0.top.bottom.leading.trailing.equalToSuperview()
        }
    }
    
    private func createLayout() -> UICollectionViewCompositionalLayout? {
        return UICollectionViewCompositionalLayout { sectionNumber, _ in
            let insetValue = 16.0
            guard let section = Section(rawValue: sectionNumber) else { return nil }
            return self.sectionLayoutFactories[section]?.makeSectionLayout(insetValue: insetValue)
        }
    }
}

// MARK: - Set Diffable DataSource
extension MainViewController {
    
    func setUpDataSource() {
        MainViewDataSourceManager.setDataSource(in: collectionView)
        MainViewDataSourceManager.snapshot(data: mockData)
    }
}

enum Section: Int, CaseIterable {
    case hero
    case nearSpot
    case recommend
}

enum SectionDataSource: Hashable {
    case hero(image: HeroImage)
    case nearSpot(spot: NearSpot)
    case recommend(recommend: Recommend)
    
    var image: String {
        switch self {
        case .hero(let image):
            return image.image
        case .nearSpot(let spot):
            return spot.image
        case .recommend(let recommend):
            return recommend.image
        }
    }
    
    var spotName: String? {
        switch self {
        case .nearSpot(let spot):
            return spot.spotName
        case .recommend(let recommend):
            return recommend.name
        default:
            return nil
        }
    }
    
    var distance: Int? {
        switch self {
        case .nearSpot(let spot):
            return spot.distance
        default:
            return nil
        }
    }
}
