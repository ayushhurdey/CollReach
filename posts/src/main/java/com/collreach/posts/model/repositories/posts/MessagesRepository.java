package com.collreach.posts.model.repositories.posts;

import com.collreach.posts.model.bo.posts.Messages;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MessagesRepository extends PagingAndSortingRepository<Messages, Integer> {
    public List<Messages> findAllByVisibilityOrVisibility(String visibility, String vis, Pageable pageable);
}
