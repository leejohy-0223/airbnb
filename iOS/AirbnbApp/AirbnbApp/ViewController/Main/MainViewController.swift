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
                                                 SectionDataSource.nearSpot(spot: NearSpot(image: "https://w7.pngwing.com/pngs/332/451/png-transparent-pepe-the-frog-pepe-4chan-television-face-leaf.png", spotName: "좋지 않은 숙소", distance: 30))
    ]
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        addViews()
        setLayouts()
        setUpDataSource()
        snapshot()
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

//MARK: - Regist Cell
private extension MainViewController {
    func createSectionHeaderRegistration() -> UICollectionView.SupplementaryRegistration<MainHeaderView> {
        UICollectionView.SupplementaryRegistration<MainHeaderView>(elementKind: MainHeaderView.ID) {
            [weak self] header, _ , indexPath in
            guard let text = self?.sectionHeaderViewModel.getTitle(at: indexPath.section) else { return }
            header.setLabel(text: text)
        }
    }
    
    func createHeroCellRegestration() -> UICollectionView.CellRegistration<HeroImageViewCell, HeroImage> {
        UICollectionView.CellRegistration<HeroImageViewCell, HeroImage> {
            [weak self] cell, _, image in
            self?.imageViewManager.fetchImage(image: image.image , onCompleted: { data in
                cell.configure(image: data)
            })
        }
    }
    
    func createNearSpotCellRegestration() -> UICollectionView.CellRegistration<NearSpotOverViewCell, NearSpot> {
        UICollectionView.CellRegistration<NearSpotOverViewCell, NearSpot> {
            [weak self] cell, _, nearSpot in
            self?.imageViewManager.fetchImage(image: nearSpot.image , onCompleted: { data in
                cell.configure(image: data, title: nearSpot.spotName, distance: nearSpot.distance)
            })
        }
    }
    
    func createRecommendCellRegestration() -> UICollectionView.CellRegistration<RecommendCardCell, Recommend> {
        UICollectionView.CellRegistration<RecommendCardCell, Recommend> {
            [weak self] cell, _, recommend in
            self?.imageViewManager.fetchImage(image: recommend.image , onCompleted: { data in
                cell.configure(image: data, title: recommend.name)
            })
        }
    }
}

// MARK: - Set Diffable DataSource
extension MainViewController {
    
    func setUpDataSource() {
        let heroCellRegistration = createHeroCellRegestration()
        let nearSpotCellRegistration = createNearSpotCellRegestration()
        let recommendResgistration = createRecommendCellRegestration()
        let sectionHeaderResigtration = createSectionHeaderRegistration()
        
        let dataSource: UICollectionViewDiffableDataSource<Section, SectionDataSource>? =
            .init(collectionView: collectionView) { collectionView, indexPath, data in
                guard let section = Section(rawValue: indexPath.section) else { return  nil }
                
                switch section {
                case .hero:
                    return collectionView.dequeueConfiguredReusableCell(
                        using: heroCellRegistration,
                        for: indexPath,
                        item: HeroImage(image: data.image))
                case .nearSpot:
                    return collectionView.dequeueConfiguredReusableCell(
                        using: nearSpotCellRegistration,
                        for: indexPath,
                        item: NearSpot(image: data.image,
                                       spotName: data.spotName ?? "",
                                       distance: data.distance ?? 0))
                case .recommend:
                    return collectionView.dequeueConfiguredReusableCell(
                        using: recommendResgistration,
                        for: indexPath,
                        item: Recommend(image: data.image,
                                        name: data.spotName ?? "")
                    )
                }
        }
        
        dataSource?.supplementaryViewProvider = { collectionView, _, indexPath in
            collectionView.dequeueConfiguredReusableSupplementary(using: sectionHeaderResigtration, for: indexPath)
        }
        
        self.dataSource = dataSource
    }
    
    private func snapshot() {
        
        var heroImages = [SectionDataSource]()
        var nearSpots = [SectionDataSource]()
        var recommends = [SectionDataSource]()
        
        mockData.forEach { (data: SectionDataSource) in
            switch data {
            case .hero:
                heroImages.append(data)
            case .nearSpot:
                nearSpots.append(data)
            case .recommend:
                recommends.append(data)
            }
        }
        
        var heroImageSnapShot = NSDiffableDataSourceSectionSnapshot<SectionDataSource>()
        heroImageSnapShot.append(heroImages)
            
        var nearSpotSnapShot = NSDiffableDataSourceSectionSnapshot<SectionDataSource>()
        nearSpotSnapShot.append(nearSpots)
        
        var recommendSnapShot = NSDiffableDataSourceSectionSnapshot<SectionDataSource>()
        recommendSnapShot.append(recommends)
        
        self.dataSource?.apply(heroImageSnapShot, to: .hero, animatingDifferences: true)
        self.dataSource?.apply(nearSpotSnapShot, to: .nearSpot, animatingDifferences: true)
        self.dataSource?.apply(recommendSnapShot, to: .recommend, animatingDifferences: true)
        
    }
}

private enum Section: Int, CaseIterable {
    case hero
    case nearSpot
    case recommend
}

private enum SectionDataSource: Hashable {
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
