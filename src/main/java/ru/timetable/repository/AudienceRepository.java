package ru.timetable.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.timetable.domain.Audience;

@Repository
public interface AudienceRepository extends PagingAndSortingRepository<Audience, Integer> {

}
