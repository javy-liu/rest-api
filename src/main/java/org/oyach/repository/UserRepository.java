package org.oyach.repository;

import org.oyach.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * description
 *
 * @author oyach
 * @since 0.0.1
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> findByUsername(@Param("username") String username);

    List<User> findByNickeName(@Param("nickname") String nickname);
}
