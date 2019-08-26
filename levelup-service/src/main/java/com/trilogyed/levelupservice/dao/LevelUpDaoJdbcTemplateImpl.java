package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LevelUpDaoJdbcTemplateImpl implements LevelUpDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public LevelUpDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final String INSERT_LEVELUP_SQL =
            "insert into level_up (customer_id, points, member_date) values (?, ?, ?) ";
    public static final String SELECT_LEVELUP_SQL =
            "select * from level_up where level_up_id = ?";
    public static final String SELECT_LEVELUP_BY_CUSTOMER_SQL =
            "select * from level_up where customer_id = ?";
    public static final String SELECT_ALL_LEVELUPS_SQL =
            "select * from level_up";
    public static final String UPDATE_LEVELUP_SQL =
            "update level_up set customer_id = ?, points = ?, member_date = ? where level_up_id = ?";
    public static final String UPDATE_LEVELUP_BY_CUSTOMER_SQL =
          "update level_up set points = ?, member_date = ? where customer_id = ?";
    public static final String DELETE_LEVELUP_SQL =
            "delete from level_up where level_up_id = ?";


    @Override
    @Transactional
    public LevelUp createLevelUp(LevelUp levelUp) {
        jdbcTemplate.update(
                INSERT_LEVELUP_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate()
        );
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        levelUp.setLevelUpId(id);
        return levelUp;
    }

    @Override
    public LevelUp getLevelUp(int levelUpId) {
        try {
            return jdbcTemplate.queryForObject(
              SELECT_LEVELUP_SQL,
              this::mapRowToLevelUp,
                    levelUpId
            );

        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    
    @Override
    public LevelUp getLevelUpByCustomer(int customerId) {
        try {
            return jdbcTemplate.queryForObject(
                  SELECT_LEVELUP_BY_CUSTOMER_SQL, this::mapRowToLevelUp, customerId);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<LevelUp> getAllLevelUps() {
        return jdbcTemplate.query(SELECT_ALL_LEVELUPS_SQL, this::mapRowToLevelUp);
    }

    @Override
    @Transactional
    public void amendLevelUp(LevelUp levelUp) {
        jdbcTemplate.update(
                UPDATE_LEVELUP_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate(),
                levelUp.getLevelUpId()
        );
    }
    
    @Override
    @Transactional
    public void amendLevelUpByCustomer(LevelUp levelUp) {
        jdbcTemplate.update(
              UPDATE_LEVELUP_BY_CUSTOMER_SQL,
              levelUp.getPoints(),
              levelUp.getMemberDate(),
              levelUp.getCustomerId()
        );
    }

    @Override
    @Transactional
    public void deleteLevelUp(int levelUpId) {
        jdbcTemplate.update(DELETE_LEVELUP_SQL, levelUpId);
    }

    // Helper Method
    private LevelUp mapRowToLevelUp(ResultSet rs, int rowNum) throws SQLException
    {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(rs.getInt("customer_id"));
        levelUp.setPoints(rs.getInt("points"));
        levelUp.setMemberDate(rs.getDate("member_date").toLocalDate());
        levelUp.setLevelUpId(rs.getInt("level_up_id"));
        return levelUp;
    }
}
