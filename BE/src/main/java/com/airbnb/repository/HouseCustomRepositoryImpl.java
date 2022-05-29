package com.airbnb.repository;

import static com.airbnb.domain.QHouse.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.airbnb.domain.House;
import com.airbnb.domain.QHouse;
import com.airbnb.utils.GeometryUtils;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class HouseCustomRepositoryImpl implements HouseCustomRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseCustomRepositoryImpl.class);

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public HouseCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<House> searchByCondition(Point position, Integer minFee, Integer maxFee) {
        double baseLatitude = position.getX();
        double baseLongitude = position.getY();
        double distance = 1000; // m 단위

        Query query = em.createNativeQuery("" +
                        "SELECT h.house_id, h.comment_count, h.max_number, h.rate, h.room_introduction, h.type, h.name, h.point, h.price, h.host_id  \n" +
                        "FROM house AS h \n" +
                        "WHERE (ST_Distance_Sphere(h.point, point(?1, ?2)) < ?3) AND h.price >= ?4 AND h.price <= ?5"
        , House.class);

        query.setParameter(1, baseLatitude);
        query.setParameter(2, baseLongitude);
        query.setParameter(3, distance);
        query.setParameter(4, minFee);
        query.setParameter(5, maxFee);

        return query.getResultList();
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
