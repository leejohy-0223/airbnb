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
    private lazy var collectionView = mapView.cardCollectionView
    private lazy var dataSource = MapCardCollectionViewDataSource(delegate: self)
    private var currentCardIndex: Int?
    
    private let startCordinate = CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.033433)
    private var mockHouseInfo = [
        HouseInfo(name: "킹왕짱 숙소", detail: Detail(rating: 4.5, reviewCount: 101), price: 85000, hostingBy: "김씨", coordinate: CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.033433)),
        HouseInfo(name: "킹왕 숙소", detail: Detail(rating: 4.45, reviewCount: 121), price: 75000, hostingBy: "박씨", coordinate: CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.032433)),
        HouseInfo(name: "킹짱 숙소", detail: Detail(rating: 4.3, reviewCount: 12112), price: 65430, hostingBy: "정씨", coordinate: CLLocationCoordinate2D(latitude: 37.48065, longitude: 127.031433)),
        HouseInfo(name: "왕짱 숙소", detail: Detail(rating: 4.221, reviewCount: 1210), price: 12350, hostingBy: "송씨", coordinate: CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.030433)),
        HouseInfo(name: "킹왕짱 숙소", detail: Detail(rating: 4.33, reviewCount: 51), price: 12350, hostingBy: "이씨", coordinate: CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.037433))
    ]
    
    private let locationManager = CLLocationManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setLocationManager()
        setMapView()
        addPins()
        setCollectionView()
    }
    
    private func setCollectionView() {
        self.collectionView.dataSource = dataSource
        self.dataSource.fetchHouseInfo(houseInfo: mockHouseInfo)
    }
    
    private func setMapView() {
        self.view = mapView
        self.mapView.delegate = self
        mapView.register(PriceAnnotationView.self,
                         forAnnotationViewWithReuseIdentifier: Constants.Pin.ID)
        self.mapView.setRegion(MKCoordinateRegion(center: startCordinate,
                                                  span: MKCoordinateSpan(
                                                    latitudeDelta: 0.01,
                                                    longitudeDelta: 0.01)),
                                                    animated: true)
    }
    
    private func addPins() {
        mockHouseInfo.forEach {
            addPin(houseInfo: $0)
        }
    }
    
    private func addPin(houseInfo: HouseInfo) {
        let pin = MKPointAnnotation()
        pin.coordinate = houseInfo.coordinate
        pin.title = houseInfo.name
        
        AddressConverter.findAddressFromCoordinate(from: houseInfo.coordinate, isCompleted: { address in
            pin.subtitle = address
        })
        
        self.mapView.addAnnotation(pin)
    }
    
    // TODO: 사용자 위치 표시
    private func setLocationManager() {
        self.locationManager.delegate = self
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()
    }
}

extension MapViewController: MKMapViewDelegate {
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        guard !annotation.isKind(of: MKUserLocation.self),
              let dequeView = mapView.dequeueReusableAnnotationView(withIdentifier: Constants.Pin.ID)
                as? PriceAnnotationView else { return nil }
       
        dequeView.annotation = annotation
        dequeView.setPrice(price: 10000000)
        
        return dequeView
    }
    
}

extension MapViewController: HeartButtonDelegate {
    func heartButtonIsTapped(_ cardIndex: Int?) {
        guard let cardIndex = cardIndex else { return }
        self.mockHouseInfo[cardIndex].isWish = !mockHouseInfo[cardIndex].isWish
        self.dataSource.changeIsWish(at: cardIndex)
    }
}

extension MapViewController: CLLocationManagerDelegate {
    
}

