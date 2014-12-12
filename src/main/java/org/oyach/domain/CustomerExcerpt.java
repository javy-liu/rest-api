package org.oyach.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * description
 *
 * @author oyach
 * @since 0.0.1
 */
//@Projection(name = "", types = User.class)
public interface CustomerExcerpt {

    String getNickname();

}
