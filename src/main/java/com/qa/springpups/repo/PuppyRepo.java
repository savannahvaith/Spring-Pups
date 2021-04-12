package com.qa.springpups.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.springpups.domain.Puppy;

@Repository
public interface PuppyRepo extends JpaRepository<Puppy,Long>{ 

}
