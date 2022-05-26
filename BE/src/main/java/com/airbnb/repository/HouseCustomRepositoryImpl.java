package com.airbnb.repository;

import com.airbnb.domain.House;
import com.airbnb.domain.Location;
import com.airbnb.utils.CardinalDirection;
import com.airbnb.utils.GeometryUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static com.airbnb.domain.QHouse.house;

public class HouseCustomRepositoryImpl implements HouseCustomRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseCustomRepositoryImpl.class);

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public HouseCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<House> searchByConditionNative(Point position, Integer minFee, Integer maxFee) {
        double baseLatitude = position.getX(); // 내 위치 y
        double baseLongitude = position.getY(); // 내 위치 x
        double distance = 0.3; // km 단위

        // 북동쪽 좌표 구하기
        Location northEast = GeometryUtils.calculateByDirection(baseLatitude, baseLongitude, distance, CardinalDirection.NORTH_EAST.getBearing());

        // 남서쪽 좌표 구하기
        Location southWest = GeometryUtils.calculateByDirection(baseLatitude, baseLongitude, distance, CardinalDirection.SOUTH_WEST.getBearing());

        double x1 = northEast.getLongitude();
        double y1 = northEast.getLatitude();
        double x2 = southWest.getLongitude();
        double y2 = southWest.getLatitude();
        LOGGER.info("[Position1] : {}, {}", x1, y1);
        LOGGER.info("[Position2] : {}, {}", x2, y2);

        // native query 활용
        Query query = em.createNativeQuery("" +
                        "SELECT h.name \n" +
                        "FROM house AS h \n" +
                        "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2) + ", h.point)",
                House.class
        );
        return query.getResultList();
        //return PageableExecutionUtils.getPage(resultList, pageable, () -> (Long) countQuery.getSingleResult());
    }

    @Override
    public List<String> searchByConditionNativeV2(Point position, Integer minFee, Integer maxFee) {
        double baseLatitude = position.getX(); // 내 위치 y
        double baseLongitude = position.getY(); // 내 위치 x
        double distance = 1000; // m 단위

        // native query 활용
        Query query = em.createNativeQuery("" +
                "SELECT h.name \n" +
                "FROM house AS h \n" +
                "WHERE (SELECT ST_Distance_Sphere(h.point, point(?1, ?2)) < ?3 or null)"
        );
        query.setParameter(1, baseLatitude);
        query.setParameter(2, baseLongitude);
        query.setParameter(3, distance);

        List resultList = query.getResultList();

        for (Object o : resultList) {
            String homename = (String)o;
            LOGGER.info("home name is : {}", homename);
        }
        return resultList;
    }
}
