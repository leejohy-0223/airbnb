//
//  Constants.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/24.
//

struct Constants {
    
    struct Pin {
        static let size: (width:Int, height:Int) = (92, 28)
        static let ID: String = "CustomPin"
    }
    
    struct Label {
        static let mapCardRatingFontSize = 16.0
        static let mapCardReviewCountFontSize = 16.0
        static let mapCardHouseNameFontSize = 24.0
        static let mapCardPriceFontSize = 16.0
        static let houseCountLabelFontSize = 32.0
        static let mainHeaderViewLabelFontSize = 32.0
    }
    
    struct Button {
        static let mapButtonWidth = 100.0
        static let mapListButton = 48.0
        static let mapTitleFontSize = 18.0
    }
    
    struct ImageViewSize {
        static let SearchResultImage = 160.0
    }
    
    struct mockData {
        
        static let mainViewInfo:MainViewInfo = MainViewInfo(heroImage: HeroImage(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSScJq6wvU3LWzlIXMX3iR_SUALhwYE4nG0hw&usqp=CAU"), NearSpot: [NearSpot(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1Q_Wx7Xpgk6Jt1FjQMamcW-0S749sYJQxlA&usqp=CAU", spotName: "서울", distance: 100),                                                                                                NearSpot(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1Q_Wx7Xpgk6Jt1FjQMamcW-0S749sYJQxlA&usqp=CAU", spotName: "서울", distance: 100),
            NearSpot(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1Q_Wx7Xpgk6Jt1FjQMamcW-0S749sYJQxlA&usqp=CAU", spotName: "서울", distance: 100),
            NearSpot(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1Q_Wx7Xpgk6Jt1FjQMamcW-0S749sYJQxlA&usqp=CAU", spotName: "서울", distance: 100)],
            recommend: [Recommend(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRrlfj2YtujOz-X8WiYPzP-8S6ffsAruJpkw&usqp=CAU", name: "김해"),
                Recommend(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRrlfj2YtujOz-X8WiYPzP-8S6ffsAruJpkw&usqp=CAU", name: "김해"),
                Recommend(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRrlfj2YtujOz-X8WiYPzP-8S6ffsAruJpkw&usqp=CAU", name: "김해"),
                Recommend(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRrlfj2YtujOz-X8WiYPzP-8S6ffsAruJpkw&usqp=CAU", name: "김해"),
                Recommend(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRrlfj2YtujOz-X8WiYPzP-8S6ffsAruJpkw&usqp=CAU", name: "김해"),
                Recommend(image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRrlfj2YtujOz-X8WiYPzP-8S6ffsAruJpkw&usqp=CAU", name: "김해")])
    }
}
