//
//  MapViewController.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/24.
//

import UIKit
import MapKit
import CoreLocation

final class MapViewController: UIViewController {
    
    private let mapView = MapView(frame: CGRect(origin: .zero, size: UIScreen.main.bounds.size))
    private lazy var collectionView = mapView.collectionView
    private lazy var dataSource = MapCardCollectionViewDataSource(delegate: self)
    private let startCordinate = CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.033433)
    
    private var houseInfoBundleViewModel: SearchResultViewModel?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setMapView()
        addPins()
        setCollectionView()
    }
    
    func getHouseInfoBundleViewModel(houseInfoBundleViewModel: SearchResultViewModel) {
        self.houseInfoBundleViewModel = houseInfoBundleViewModel
    }
    
    private func setCollectionView() {
        self.collectionView.dataSource = dataSource
        self.dataSource.fetchHouseInfo(houseInfo: houseInfoBundleViewModel?.houseInfoBundle ?? [])
    }
    
    private func setMapView() {
        self.view = mapView
        self.mapView.delegate = self
        self.mapView.listButtonDelegate = self
        mapView.register(PriceAnnotationView.self,
                         forAnnotationViewWithReuseIdentifier: Constants.Pin.ID)
        self.mapView.setRegion(MKCoordinateRegion(center: startCordinate,
                                                  span: MKCoordinateSpan(
                                                    latitudeDelta: 0.01,
                                                    longitudeDelta: 0.01)),
                                                    animated: true)
    }
    
    private func addPins() {
        houseInfoBundleViewModel?.houseInfoBundle.forEach {
            addPin(houseInfo: $0)
        }
    }
    
    private func addPin(houseInfo: HouseInfo) {
        let pin = MKPointAnnotation()
        let coordinate = CLLocationCoordinate2D(latitude: houseInfo.latitude, longitude: houseInfo.longitude)
        pin.coordinate = coordinate
        pin.title = houseInfo.name
        AddressConverter.findAddressFromCoordinate(from: coordinate, isCompleted: { address in
            pin.subtitle = address
        })

        self.mapView.addAnnotation(pin)
    }
}

extension MapViewController: MKMapViewDelegate {
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        guard !annotation.isKind(of: MKUserLocation.self),
              let dequeView = mapView.dequeueReusableAnnotationView(withIdentifier: Constants.Pin.ID)
                as? PriceAnnotationView else { return nil }
        
        dequeView.annotation = annotation

        // 특정 집 정보 coordinate로 가져오기
        let houseInfo = self.houseInfoBundleViewModel?.houseInfoBundle.first {
            let coordinate = CLLocationCoordinate2D(latitude: $0.latitude, longitude: $0.longitude)
            return coordinate == annotation.coordinate
        }
        
        dequeView.setPrice(price: houseInfo?.price ?? 0)

        return dequeView
    }
    
}

extension MapViewController: HeartButtonDelegate {
    func heartButtonIsTapped(_ cardIndex: Int?) {
        self.houseInfoBundleViewModel?.changeIsWish(cardIndex)
    }
}

extension MapViewController: ListButtonDelegate {
    func listButtonisTapped() {
        self.dismiss(animated: true)
    }
}
