//
//  MapView.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/24.
//

import UIKit
import MapKit
import SnapKit

final class MapView: MKMapView {
    
    weak var listButtonDelegate: ListButtonDelegate?
    
    lazy var collectionView: UICollectionView = {
        let cellWidth = self.frame.width - 60
        let cellHeight = self.frame.height / 6
        
        let flowLayout = UICollectionViewFlowLayout()
        flowLayout.itemSize = CGSize(
            width: cellWidth,
            height: cellHeight)
        
        flowLayout.minimumLineSpacing = 16.0
        flowLayout.scrollDirection = .horizontal
        
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: flowLayout)
        collectionView.layer.cornerRadius = 8.0
        collectionView.backgroundColor = .clear
        
        return collectionView
    }()
    
    private lazy var listButton: UIButton = {
        let button = UIButton()
        let image = UIImage(systemName: "line.3.horizontal", withConfiguration: UIImage.SymbolConfiguration(pointSize: 26))
        
        button.backgroundColor = .systemBackground
        button.layer.cornerRadius = Constants.Button.mapListButton / 2
        button.tintColor = .label
        button.setImage(image, for: .normal)
        return button
    }()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.mapType = .standard
        addViews()
        setCollectionView()
        setButton()
    }
    
    @available(*, unavailable) required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func addViews() {
        [collectionView, listButton].forEach {
            self.addSubview($0)
        }
    }
    
    private func setButton() {
        listButton.addTarget(self, action: #selector(listButtonTapped), for: .touchUpInside)
        
        listButton.snp.makeConstraints {
            $0.leading.equalToSuperview().inset(24)
            $0.top.equalToSuperview().inset(64)
            $0.width.equalTo(Constants.Button.mapListButton)
            $0.height.equalTo(Constants.Button.mapListButton)
        }
    }
    
    @objc private func listButtonTapped() {
        listButtonDelegate?.listButtonisTapped()
    }
    
    private func setCollectionView() {
        self.collectionView.register(MapViewCardCell.self, forCellWithReuseIdentifier: MapViewCardCell.ID)
        
        collectionView.snp.makeConstraints {
            $0.leading.trailing.equalToSuperview().inset(8.0)
            $0.bottom.equalTo(self.snp.bottom).inset(32.0)
            $0.height.equalTo(self.frame.height / 6)
        }
    }
}
