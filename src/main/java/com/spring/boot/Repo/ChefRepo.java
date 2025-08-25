package com.spring.boot.Repo;

import com.spring.boot.Model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepo extends JpaRepository<Chef, Long> {
}
