package org.dezzzl.zoo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.entity.pet.FeedType;
import org.dezzzl.zoo.entity.pet.FeedingRation;
import org.dezzzl.zoo.repository.FeedingRationRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class FeedingRationRepositoryImpl implements FeedingRationRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    //language=PostgreSQL
    private static final String FIND_BY_ID = """
            SELECT fr.id, fr.name, ft.id, ft.name
            FROM feeding_ration fr
            JOIN feed_type ft on ft.id = fr.feed_type_id
            WHERE fr.id = :id;
            """;

    private static final String FIND_ALL = """
            SELECT fr.id, fr.name, ft.id, ft.name
            FROM feeding_ration fr
            JOIN feed_type ft on ft.id = fr.feed_type_id
            """;

    private static final String INSERT = """
            INSERT INTO feeding_ration (name, feed_type_id)
            VALUES (:name, :feedTypeId)
            RETURNING id;
            """;

    private static final String UPDATE = """
            UPDATE feeding_ration
            SET name = :name, feed_type_id = :feedTypeId
            WHERE id = :id;
            """;

    private static final String DELETE = """
        DELETE FROM feeding_ration
        WHERE id = :id;
        """;

    public Optional<FeedingRation> findById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID,
                new MapSqlParameterSource("id", id), // передача параметра id
                (rs, rowNum) -> new FeedingRation(
                        rs.getInt("ration_id"),       // id рациона кормления
                        rs.getString("ration_name"),  // название рациона
                        new FeedType(                 // создание объекта FeedType
                                rs.getInt("feed_type_id"),   // id типа рациона
                                rs.getString("feed_type_name") // название типа рациона
                        )
                )));
    }

    public List<FeedingRation> findAll() {
        return jdbcTemplate.query(FIND_ALL,
                (rs, rowNum) -> new FeedingRation(
                        rs.getInt("fr.id"),       // id рациона кормления
                        rs.getString("fr.name"),  // название рациона
                        new FeedType(                 // создание объекта FeedType
                                rs.getInt("fd.id"),   // id типа рациона
                                rs.getString("fd.name") // название типа рациона
                        )
                ));
    }

    public FeedingRation save(FeedingRation feedingRation) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", feedingRation.getName());
        params.addValue("feedTypeId", feedingRation.getFeedType().getId());

        Integer id = jdbcTemplate.queryForObject(INSERT, params, Integer.class);
        feedingRation.setId(id);

        return feedingRation;
    }

    public Integer update(FeedingRation feedingRation) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", feedingRation.getId());
        params.addValue("name", feedingRation.getName());
        params.addValue("feedTypeId", feedingRation.getFeedType().getId());

        // Выполняем запрос обновления и возвращаем количество обновленных строк
        return jdbcTemplate.update(UPDATE, params);
    }

    public Integer delete(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        // Выполняем запрос удаления и возвращаем количество удаленных строк
        return jdbcTemplate.update(DELETE, params);
    }

}
