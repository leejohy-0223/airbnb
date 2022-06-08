//
//  SearchSpotViewController.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/06.
//

import UIKit
import SnapKit
import MapKit

final class SearchSpotViewController: UIViewController {
    
    private lazy var collectionView: UICollectionView = {
        guard let layout = self.createLayout() else { return UICollectionView() }
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: layout)
        return collectionView
    }()

    private var sectionLayoutFactory: SectionLayoutCreator.Type = SearchSpotViewLayoutFactoy.self

    private var searchCompleter = MKLocalSearchCompleter()
    private var searchResults = [MKLocalSearchCompletion]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setSearchCompleter()
        setCollectionView()
        setDataSource()
    }

    private func createLayout() -> UICollectionViewCompositionalLayout? {
        return UICollectionViewCompositionalLayout.init { _, _ in
            self.sectionLayoutFactory.makeSectionLayout(insetValue: 16.0)
        }
    }

    private func setDataSource() {
        SearchSpotViewSectionManager.setDataSource(in: collectionView)
    }

    private func setCollectionView() {
        self.view.addSubview(collectionView)

        collectionView.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
    }
    
    private func setSearchCompleter() {
        searchCompleter.delegate = self
        searchCompleter.resultTypes = .address
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
