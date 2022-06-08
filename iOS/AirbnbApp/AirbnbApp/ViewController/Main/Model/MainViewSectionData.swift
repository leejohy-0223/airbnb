//
//  MainViewSectionData.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/07.
//

enum MainViewSectionData: Hashable {
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
