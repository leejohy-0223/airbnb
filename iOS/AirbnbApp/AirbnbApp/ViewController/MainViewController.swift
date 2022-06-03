//
//  MainViewController.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/02.
//

import UIKit

final class MainViewController: UIViewController {
    
    private lazy var collectionView: UICollectionView = {
        guard let layout = self.createLayout() else { return UICollectionView() }
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: layout)
        return collectionView
    }()
    
    private let dataSource = MainCollectionViewDataSource()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        addViews()
        setCollectionView()
    }
    
    private func addViews() {
        [collectionView].forEach {
            self.view.addSubview($0)
        }
    }
    
    private func setCollectionView() {
        self.collectionView.dataSource = dataSource
        self.collectionView.register(HeroImageViewCell.self, forCellWithReuseIdentifier: HeroImageViewCell.ID)
        self.collectionView.register(NearSpotOverViewCell.self, forCellWithReuseIdentifier: NearSpotOverViewCell.ID)
        self.collectionView.register(RecommendCardCell.self, forCellWithReuseIdentifier: RecommendCardCell.ID)
        self.collectionView.register(MainHeaderView.self,
                                     forSupplementaryViewOfKind: MainHeaderView.ID,
                                     withReuseIdentifier: MainHeaderView.ID)
        
        self.collectionView.snp.makeConstraints{
            $0.edges.equalToSuperview()
        }
    }
    
    private func createLayout() -> UICollectionViewCompositionalLayout? {
        return UICollectionViewCompositionalLayout { sectionNumber, env in
            let insetValue = 16.0
            switch sectionNumber {
            case 0:
                let item = NSCollectionLayoutItem(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(1),
                                                                                     heightDimension: .fractionalHeight(1)))
                item.contentInsets = NSDirectionalEdgeInsets(top: insetValue,
                                                             leading: insetValue,
                                                             bottom: insetValue,
                                                             trailing: insetValue)
                
                let group = NSCollectionLayoutGroup.horizontal(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(1),
                                                                                                  heightDimension: .absolute(300)),
                                                                                                  subitems: [item])
                let section = NSCollectionLayoutSection(group: group)
                return section
                
            case 1:
                let item = NSCollectionLayoutItem(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(0.8),
                                                                                     heightDimension: .fractionalHeight(0.2)))
                item.contentInsets = NSDirectionalEdgeInsets(top: insetValue,
                                                             leading: insetValue,
                                                             bottom: insetValue,
                                                             trailing: -insetValue)
                
                let group = NSCollectionLayoutGroup.vertical(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(0.7),
                                                                                                heightDimension: .fractionalHeight(0.25)),
                                                                                                subitem: item, count: 2)
                
                let section = NSCollectionLayoutSection(group: group)
                section.orthogonalScrollingBehavior = .continuous
                
                section.boundarySupplementaryItems = [NSCollectionLayoutBoundarySupplementaryItem(layoutSize: NSCollectionLayoutSize(
                    widthDimension: .fractionalWidth(1),
                    heightDimension: .estimated(50)),
                    elementKind: MainHeaderView.ID,
                    alignment: .topLeading)]
                
                return section
                
            case 2:
                let item = NSCollectionLayoutItem(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(1),
                                                                                     heightDimension: .fractionalHeight(1)))
                item.contentInsets = NSDirectionalEdgeInsets(top: 0,
                                                             leading: insetValue,
                                                             bottom: 0,
                                                             trailing: 0)
                
                let group = NSCollectionLayoutGroup.horizontal(layoutSize: NSCollectionLayoutSize(widthDimension: .fractionalWidth(0.7),
                                                                                                heightDimension: .estimated(400)),
                                                                                                subitems: [item])
                
                let section = NSCollectionLayoutSection(group: group)
                section.orthogonalScrollingBehavior = .continuous
                
                section.boundarySupplementaryItems = [NSCollectionLayoutBoundarySupplementaryItem(layoutSize: NSCollectionLayoutSize(
                    widthDimension: .fractionalWidth(1),
                    heightDimension: .estimated(50)),
                    elementKind: MainHeaderView.ID,
                    alignment: .topLeading)]
                
                return section
                
            default:
                return nil
            }
        }
    }
}
