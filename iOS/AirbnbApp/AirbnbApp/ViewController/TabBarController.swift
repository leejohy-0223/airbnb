//
//  TabBarController.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/24.
//

import UIKit

final class TabBarController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Create Tab Items
        let mainVC = UINavigationController(rootViewController: MainViewController())
        let tabMainBarItem = UITabBarItem(title: "검색", image: UIImage(systemName: "magnifyingglass"), tag: 0)
        
        mainVC.tabBarItem = tabMainBarItem
        
        let wishVC = WishListViewController()
        let tabWishBarItem = UITabBarItem(title: "위시리스트", image: UIImage(systemName: "heart"), tag: 1)
        
        wishVC.tabBarItem = tabWishBarItem
        
        let reservationVC = ReservationViewController()
        let tabReservationBarItem = UITabBarItem(title: "내예약", image: UIImage(systemName: "person"), tag: 2)
        
        reservationVC.tabBarItem = tabReservationBarItem
        
        self.viewControllers = [mainVC, wishVC, reservationVC]
    }

}


