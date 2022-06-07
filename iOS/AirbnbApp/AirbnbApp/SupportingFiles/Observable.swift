//
//  Observable.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/06/01.
//

import Foundation

final class Observable<T> {
    
    private var listener: ((T?) -> Void)?
    
    var value: T? {
        didSet {
            listener?(value)
        }
    }
    
    init(_ value: T?) {
        self.value = value
    }
    
    func bind(_ clouser: @escaping (T?) -> Void) {
        clouser(value)
        self.listener = clouser
    }
}
