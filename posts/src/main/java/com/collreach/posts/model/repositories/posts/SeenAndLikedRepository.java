package com.collreach.posts.model.repositories.posts;

import com.collreach.posts.model.bo.posts.Messages;
import com.collreach.posts.model.bo.posts.SeenAndLiked;
import com.collreach.posts.model.bo.posts.UserMessageKey;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SeenAndLikedRepository extends CrudRepository<SeenAndLiked, UserMessageKey> {
    //void deleteAllByMessageId(Messages messageId);
    @Modifying
    @Transactional
    @Query("delete from SeenAndLiked sal where sal.messageId = :messageId")
    void deleteAllByMessageId(@Param("messageId") Messages messageId);
}
