package com.collreach.posts.model.repositories.polls;

import com.collreach.posts.model.bo.Users;
import com.collreach.posts.model.bo.polls.Polls;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PollsRepository extends PagingAndSortingRepository<Polls, Integer> {
    public List<Polls> findAllByVisibilityOrVisibility(String visibility, String vis, Pageable pageable);
    public List<Polls> findAllByUserId(Users userId, Pageable pageable);
}
