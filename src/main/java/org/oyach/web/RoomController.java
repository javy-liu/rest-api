package org.oyach.web;

import org.oyach.domain.User;
import org.oyach.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * description
 *
 * @author oyach
 * @since 0.0.1
 */
@RestController
public class RoomController implements ResourceProcessor<RepositoryLinksResource> {

    public static final String ENTER_REL = "enter";

    @Autowired
    private UserService userService;


    @RequestMapping("/enter")
    public HttpEntity<Resources<Resource<User>>> showUsersInProgress() {

        Resources<Resource<User>> userResources = Resources.wrap(userService.findUsers());

        userResources.add(linkTo(methodOn(RoomController.class).showUsersInProgress()).withSelfRel());

        return new ResponseEntity<Resources<Resource<User>>>(userResources, HttpStatus.OK);
    }


    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        resource.add(linkTo(methodOn(RoomController.class).showUsersInProgress()).withRel(ENTER_REL));

        return resource;
    }
}
