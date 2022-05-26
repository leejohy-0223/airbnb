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
    
    private let startCordinate = CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.033433)
    
    private let mockCordinates = [
        HouseInfo(coordinate: CLLocationCoordinate2D(latitude: 37.490765, longitude: 127.033433), name: "킹왕짱 숙소"),
        HouseInfo(coordinate: CLLocationCoordinate2D(latitude: 37.491545, longitude: 127.033433), name: "킹 숙소"),
        HouseInfo(coordinate: CLLocationCoordinate2D(latitude: 37.492345, longitude: 127.033433), name: "왕 숙소"),
        HouseInfo(coordinate: CLLocationCoordinate2D(latitude: 37.493455, longitude: 127.033433), name: "짱 숙소")
    ]
    
    private let locationManager = CLLocationManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setLocationManager()
        setMapView()
        addPins()
        self.mapView.cardCollectionView.dataSource = self
        self.mapView.cardCollectionView.register(MapViewCardCell.self, forCellWithReuseIdentifier: Constants.CellID.map)
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
        mockCordinates.forEach {
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

extension MapViewController: UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 5
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: Constants.CellID.map,
                                                            for: indexPath) as? MapViewCardCell
        else { return UICollectionViewCell() }
        cell.setReviewLabel(rating: 4.72, reviewCount: 128)
        cell.setImage(image: UIImage(systemName: "house")!)
        cell.setPrice(price: 82587)
        cell.setHouseName(houseName: "한국 어딘가 아주 좋은 킹왕짱 숙소 이름인데 조금 길어")
        return cell
    }
}

extension MapViewController: CLLocationManagerDelegate {
    
}

