//
//  SearchSpotViewController.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/06.
//

import UIKit
import SnapKit
import MapKit

typealias SearchedSpot = NearSpot

final class SearchSpotViewController: UIViewController {
    
    private lazy var searchCollectionView: UICollectionView = {
        guard let layout = self.createLayout(.search) else { return UICollectionView() }
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: layout)
        return collectionView
    }()
    
    private lazy var nearSpotCollectionView: UICollectionView = {
        guard let layout = self.createLayout(.nearSpot) else { return UICollectionView() }
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: layout)
        return collectionView
    }()
    
    private var layoutFactories: [Layout: SectionLayoutCreator.Type] = [.search: SearchSpotViewLayoutFactoy.self,
                                                                        .nearSpot: NearSearchSectionLayoutFactory.self]
    
    private var searchCompleter = MKLocalSearchCompleter()
    private var searchResults = [MKLocalSearchCompletion]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setSearchCompleter()
        setCollectionView()
        setDataSource()
    }

    private func createLayout(_ layout: Layout) -> UICollectionViewCompositionalLayout? {
        return UICollectionViewCompositionalLayout.init { _, _ in
            self.layoutFactories[layout]?.makeSectionLayout(insetValue: 16.0)
        }
    }

    private func setDataSource() {
        SearchSpotViewSectionManager.setDataSource(in: searchCollectionView)
        SearchSpotViewSectionManager.setDataSource(in: nearSpotCollectionView)
    }

    private func setCollectionView() {
        self.view.addSubview(searchCollectionView)

        searchCollectionView.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
    }
    
    private func setSearchCompleter() {
        searchCompleter.delegate = self
        searchCompleter.resultTypes = .address
    }
    
    private enum Layout: String {
        case search
        case nearSpot
    }
}

extension SearchSpotViewController: MKLocalSearchCompleterDelegate {
    func completerDidUpdateResults(_ completer: MKLocalSearchCompleter) {
        searchResults = completer.results
    }
}

extension SearchSpotViewController: UISearchResultsUpdating {
    func updateSearchResults(for searchController: UISearchController) {
        guard let searchText = searchController.searchBar.text else { return }
        searchCompleter.queryFragment = searchText
        SearchSpotViewSectionManager.snapshot(data: searchResults)
    }
}

enum SearchSpotViewSection: Int, CaseIterable {
    case searchResult
}

enum SearchSpotViewSectionData: Hashable {
    case searchResult(searchSpot: SearchedSpot)
    
    var image: String {
        switch self {
        case .searchResult(let searchedSpot):
            return searchedSpot.image
        }
    }
    
    var spotName: String {
        switch self {
        case .searchResult(let searchedSpot):
            return searchedSpot.spotName
        }
    }
}


