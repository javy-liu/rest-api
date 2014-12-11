package org.oyach.service.impl;

import org.oyach.domain.User;
import org.oyach.service.UserService;
import org.oyach.service.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author oyach
 * @since 0.0.1
 */
@Service
public class UserServiceImpl implements UserService{

    @Override
    public UserVO getUser(String username) {

        return null;
    }

    @Override
    public UserVO findUser(String username) {
        return null;
    }

    @Override
    public List<User> findUsers() {
        List<User> users = new ArrayList<User>();
        User oyach = new User();
        oyach.setUsername("oyach");
        oyach.setNickname("欧阳澄泓");
        oyach.setId(1L);

        users.add(oyach);

        User faith = new User();
        faith.setUsername("faith");
        faith.setNickname("欧阳澄泓");
        faith.setId(2L);

        users.add(faith);

        return users;
    }
}
