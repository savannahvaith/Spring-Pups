package com.qa.springpups.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qa.springpups.domain.Owner;

public interface OwnerRepo extends JpaRepository<Owner, Long>{

}
