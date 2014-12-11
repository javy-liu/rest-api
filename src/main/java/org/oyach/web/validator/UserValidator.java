package org.oyach.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * description
 *
 * @author oyach
 * @since 0.0.1
 */
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
