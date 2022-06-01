//
//  EndPoint.swift
//  SideDishApp
//
//  Created by 박진섭 on 2022/04/18.
//

import Alamofire

protocol Endpointable {
    func getHttpMethod() -> Alamofire.HTTPMethod
    func getBaseURL() -> BaseURL
    func getPath() -> Path
    func getHeaders() -> [String: String]?
    func getBody() -> [String: Any]?
}

extension Endpointable {
    func getURL() -> URLConvertible {
        return getBaseURL().urlString + getPath().pathString
    }
}

struct Endpoint: Endpointable {
    private let httpMethod: HTTPMethod
    private let baseURL: BaseURL
    private let path: Path
    private let headers: [String: String]?
    private let body: [String: Any]?
    
    init(httpMethod: HTTPMethod, baseURL: BaseURL, path: Path, headers: [String: String], body: [String: String]? = nil) {
        self.httpMethod = httpMethod
        self.baseURL = baseURL
        self.path = path
        self.headers = headers
        self.body = body
    }
    
    func getHttpMethod() -> HTTPMethod {
        return self.httpMethod
    }
    
    func getBaseURL() -> BaseURL {
        return self.baseURL
    }
    
    func getPath() -> Path {
        return self.path
    }
    
    func getHeaders() -> [String: String]? {
        return self.headers
    }
    
    func getBody() -> [String: Any]? {
        return self.body
    }
}

enum EndPointCase {
    case getHousesPirce
    case getHousesInfo
    case getDetail(id: String)
    
    var endpoint: Endpointable {
        switch self {
        case .getHousesPirce:
            return Endpoint(httpMethod: .get,
                            baseURL: .main,
                            path: .getHousesPirce,
                            headers: ["Content-Type": "application/json"],
                            body: nil)
            
        case .getHousesInfo:
            return Endpoint(httpMethod: .get,
                            baseURL: .main,
                            path: .getHousesInfo,
                            headers: ["Content-Type": "application/json"],
                            body: nil)
            
        case .getDetail(let hash):
            return Endpoint(httpMethod: .get,
                            baseURL: .main,
                            path: .getDetail(id: hash),
                            headers: ["Content-Type": "application/json"],
                            body: nil)
        }
    }
}

enum BaseURL {
    case main
    
    var urlString: String {
        switch self {
        case .main:
            return "http://54.180.156.158"
        }
    }
}

enum Path {
    case getHousesPirce
    case getHousesInfo
    case getDetail(id: String)
    
    var pathString: String {
        switch self {
        case .getHousesPirce:
            return "api/houses/price"
        case .getHousesInfo:
            return "api/houses"
        case .getDetail(let id):
            return "api/houses/\(id)"
        }
    }
}
