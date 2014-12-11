package org.oyach.web.processor;

import org.oyach.domain.User;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author oyach
 * @since 0.0.1
 */
@Component
public class UserResourceProcessor implements ResourceProcessor<Resource<User>> {

    @Override
    public Resource<User> process(Resource<User> resource) {
        User user = resource.getContent();
        user.setPassword(null);

        return resource;
    }
}
