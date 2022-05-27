package com.airbnb.repository;

import com.airbnb.domain.House;
import com.airbnb.domain.QHouse;
import com.airbnb.utils.GeometryUtils;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public List<String> searchByCondition(Point position, Integer minFee, Integer maxFee) {
        double baseLatitude = position.getX(); // 내 위치 y
        double baseLongitude = position.getY(); // 내 위치 x
        double distance = 1000; // m 단위

        // native query 활용
        Query query = em.createNativeQuery("" +
                "SELECT h.name \n" +
                "FROM house AS h \n" +
                "WHERE (ST_Distance_Sphere(h.point, point(?1, ?2)) < ?3)"
        );
        query.setParameter(1, baseLatitude);
        query.setParameter(2, baseLongitude);
        query.setParameter(3, distance);

        List resultList = query.getResultList();

        for (Object o : resultList) {
            String homename = (String) o;
            LOGGER.info("home name is : {}", homename);
        }
        return resultList;
    }

    @Override
    public List<House> searchByConditionQueryDsl(Point position, Integer minFee, Integer maxFee) {
        double baseLatitude = position.getX(); // 내 위치 y
        double baseLongitude = position.getY(); // 내 위치 x
        double distance = 1000; // m 단위

        QHouse subHouse = new QHouse("subHouse");

        List<House> results = queryFactory
                .selectFrom(house)
                .where(
                        Expressions.stringTemplate("function('ST_Distance_Sphere', {0}, {1})", subHouse.point, GeometryUtils.toPoint(baseLatitude, baseLongitude))
                                .as("subDistance").loe(String.valueOf(distance))
                ).fetch();

        return results;
    }
}
