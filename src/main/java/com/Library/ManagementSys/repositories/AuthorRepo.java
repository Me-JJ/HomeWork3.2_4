package com.Library.ManagementSys.repositories;

import com.Library.ManagementSys.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepo extends JpaRepository<AuthorEntity,Long> {
    List<AuthorEntity> findByNameContaining(String name);
}
