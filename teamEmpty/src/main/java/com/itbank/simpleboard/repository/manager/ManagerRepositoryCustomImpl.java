package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.dto.ManagerDTO;
import com.itbank.simpleboard.dto.QManagerDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itbank.simpleboard.entity.QManager.*;
import static com.itbank.simpleboard.entity.QUser.user;

@Repository
public class ManagerRepositoryCustomImpl implements ManagerRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Autowired
    public ManagerRepositoryCustomImpl(EntityManager em, EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(em);
        this.entityManager = entityManager;
    }

    @Override
    public List<ManagerDTO> findBySearchType(String searchType, String searchValue) {
        BooleanExpression condition;

        if("name".equals(searchType)){
            condition = manager.user.user_name.likeIgnoreCase("%"+searchValue+"%");
        } else if ("phone".equals(searchType)) {
            condition = manager.user.pnum.likeIgnoreCase("%"+searchValue+"%");
        }
        else {
            throw new IllegalArgumentException("유효하지 않은 검색타입입니다.");
        }

        return queryFactory
                .select(new QManagerDTO(
                        manager.manager_img,
                        user.user_id,
                        user.user_name,
                        user.pnum,
                        user.email,
                        manager.hireDate
                ))
                .from(manager)
                .innerJoin(user)
                .on(manager.user.user_id.eq(user.user_id))
                .where(
                    condition
                )
                .fetch();
    }
}
