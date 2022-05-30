//
//  AddressConverter.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/25.
//

import MapKit

struct AddressConverter {
    static func findAddressFromCoordinate(from coordinate : CLLocationCoordinate2D, isCompleted: @escaping (String) -> Void) {
        let findLocation = CLLocation(latitude: coordinate.latitude, longitude: coordinate.longitude)
        let geocoder = CLGeocoder()
        let locale = Locale(identifier: "Ko-kr")
        
        geocoder.reverseGeocodeLocation(findLocation, preferredLocale: locale, completionHandler: {(placemarks, error) in
            let placeMark: CLPlacemark? = placemarks?[0]
            
            var address: String = ""
            // 서울
            if let administrativeArea = placeMark?.administrativeArea {
                address += administrativeArea
            }
            // 강남구
            if let area: String = placeMark?.locality{
                address += " "
                address += area
            }
            
            // 강남대로62길 23
            if let name: String = placeMark?.name {
                address += " "
                address += name
                }
                isCompleted(address)
            
            }
        )
    }
}
