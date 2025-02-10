package com.Library.ManagementSys.repositories;

import com.Library.ManagementSys.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<BookEntity,Long>
{

    List<BookEntity> findByPublishedYearAfter(Integer year);

    List<BookEntity> findByTitleContaining(String title);
}
