package org.oyach.service;

import org.oyach.domain.User;
import org.oyach.service.vo.UserVO;

import java.util.List;

/**
 * description
 *
 * @author oyach
 * @since 0.0.1
 */
public interface UserService {

    UserVO getUser(String username);

    UserVO findUser(String username);

    List<User> findUsers();
}
