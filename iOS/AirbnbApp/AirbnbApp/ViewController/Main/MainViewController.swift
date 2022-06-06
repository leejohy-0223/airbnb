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
    private var sections: [Int : MainViewSectionCreator.Type] = [0: HeroImageSectionFactory.self,
                                                                 1: NearSpotSectionFactory.self,
                                                                 2: RecommendSectionFactory.self]
    
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
            return self.sections[sectionNumber]?.makeSectionLayout(insetValue: insetValue)
        }
    }
}
