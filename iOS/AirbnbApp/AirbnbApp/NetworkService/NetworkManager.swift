//
//  NetworkManager.swift
//  AirbnbApp
//
//  Created by 박진섭 on 2022/05/31.
//

import Alamofire
import Foundation.NSData

struct NetworkManager: NetworkManagable {

    private let sessionManager: Session
    
    init(sessionManager: Session) {
        self.sessionManager = sessionManager
    }
    
    func request<T: Codable>(endpoint: Endpointable, completion: @escaping ((DataResponse<T?, AFError>) -> Void))  {
        let headers = endpoint.getHeaders()
        let url = endpoint.getURL()
        let method = endpoint.getHttpMethod()
        let param = endpoint.getBody()
        
        sessionManager
            .request(url, method: method, parameters: param, encoding: URLEncoding.default, headers: HTTPHeaders(headers))
            .validate()
            .responseDecodable(completionHandler: completion)
    }
    
    func requestImage(url: String, completion: @escaping ((DataResponse<Data?, AFError>) -> Void))  {
        sessionManager
            .request(url, method: .get, parameters: nil, encoding: URLEncoding.default, headers: nil)
            .validate()
            .response(completionHandler: completion)
    }
}


