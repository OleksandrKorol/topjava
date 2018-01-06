package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal entity);

    @Override
    Optional<Meal> findById(Integer id);

    @Query(name = Meal.ALL_SORTED)
    List<Meal> findAllByUserId(@Param("userId") Integer userId);

    @Transactional
    @Query(name = Meal.DELETE)
    int deleteByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> findByCreatedDateBetween(@Param("startDate") LocalDateTime start, @Param("endDate") LocalDateTime end,
            @Param("userId") Integer userId);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = ?1 AND m.user.id = ?2 ORDER BY m.dateTime DESC")
    Meal getWithUser(Integer id, Integer userId);
}
