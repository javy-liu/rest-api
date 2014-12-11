package org.oyach.handler;

import org.oyach.domain.User;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * description
 *
 * @author oyach
 * @since 0.0.1
 */
@Component
@RepositoryEventHandler(User.class)
public class UserHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(@Valid User user) {
        System.out.println("----------------");
    }


}
