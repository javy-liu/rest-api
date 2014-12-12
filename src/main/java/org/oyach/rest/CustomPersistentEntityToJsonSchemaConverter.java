package org.oyach.rest;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.SimplePropertyHandler;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.rest.core.Path;
import org.springframework.data.rest.core.mapping.ResourceDescription;
import org.springframework.data.rest.core.mapping.ResourceMapping;
import org.springframework.data.rest.core.mapping.ResourceMappings;
import org.springframework.data.rest.core.mapping.ResourceMetadata;
import org.springframework.data.rest.webmvc.json.JsonSchema;
import org.springframework.data.rest.webmvc.json.PersistentEntityToJsonSchemaConverter;
import org.springframework.data.rest.webmvc.mapping.AssociationLinks;
import org.springframework.data.rest.webmvc.mapping.LinkCollectingAssociationHandler;
import org.springframework.data.util.TypeInformation;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.StringUtils.uncapitalize;

/**
 * spring data rest 默认没有提供schema的 required功能，为了满足使用进行了扩展
 *
 * @author oyach
 * @since 0.0.1
 */
public class CustomPersistentEntityToJsonSchemaConverter extends PersistentEntityToJsonSchemaConverter {

    private final Set<ConvertiblePair> convertiblePairs = new HashSet<ConvertiblePair>();
    private final ResourceMappings mappings;
    private final PersistentEntities repositories;
    private final MessageSourceAccessor accessor;
    private final EntityLinks entityLinks;

    /**
     * Creates a new {@link org.springframework.data.rest.webmvc.json.PersistentEntityToJsonSchemaConverter} for the given {@link org.springframework.data.mapping.context.PersistentEntities} and
     * {@link org.springframework.data.rest.core.mapping.ResourceMappings}.
     *
     * @param entities    must not be {@literal null}.
     * @param mappings    must not be {@literal null}.
     * @param accessor
     * @param entityLinks
     */
    public CustomPersistentEntityToJsonSchemaConverter(PersistentEntities entities, ResourceMappings mappings, MessageSourceAccessor accessor, EntityLinks entityLinks) {
        super(entities, mappings, accessor, entityLinks);
        Assert.notNull(entities, "PersistentEntities must not be null!");
        Assert.notNull(mappings, "ResourceMappings must not be null!");
        Assert.notNull(accessor, "MessageSourceAccessor must not be null!");
        Assert.notNull(entityLinks, "EntityLinks must not be null!");

        this.repositories = entities;
        this.mappings = mappings;
        this.accessor = accessor;
        this.entityLinks = entityLinks;

        for (TypeInformation<?> domainType : entities.getManagedTypes()) {
            convertiblePairs.add(new ConvertiblePair(domainType.getType(), JsonSchema.class));
        }
    }


    @Override
    public JsonSchema convert(Class<?> domainType) {
        return super.convert(domainType);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        System.out.println("==================");
        System.out.println(source);
        System.out.println(sourceType);
        System.out.println(targetType);

        final PersistentEntity<?, ?> persistentEntity = repositories.getPersistentEntity((Class<?>) source);
        final ResourceMetadata metadata = mappings.getMappingFor(persistentEntity.getType());
        final JsonSchema jsonSchema = new JsonSchema(persistentEntity.getName(),
                resolveMessage(metadata.getItemResourceDescription()));

        persistentEntity.doWithProperties(new SimplePropertyHandler() {

            /*
             * (non-Javadoc)
             * @see org.springframework.data.mapping.PropertyHandler#doWithPersistentProperty(org.springframework.data.mapping.PersistentProperty)
             */
            @Override
            public void doWithPersistentProperty(PersistentProperty<?> persistentProperty) {

                Class<?> propertyType = persistentProperty.getType();
                String type = uncapitalize(propertyType.getSimpleName());

                ResourceMapping propertyMapping = metadata.getMappingFor(persistentProperty);
                ResourceDescription description = propertyMapping.getDescription();
                String message = resolveMessage(description);

                NotNull notNull = persistentProperty.findPropertyOrOwnerAnnotation(NotNull.class);
                NotEmpty notEmpty = persistentProperty.findPropertyOrOwnerAnnotation(NotEmpty.class);
                Length length = persistentProperty.findPropertyOrOwnerAnnotation(Length.class);

                boolean required = false;

                if (notNull != null || notEmpty != null || (length != null && length.min() != 0)){
                    required = true;
                }

                JsonSchema.Property property = persistentProperty.isCollectionLike() ? //
                        new JsonSchema.ArrayProperty("array", message, required)
                        : new JsonSchema.Property(type, message, required);

                jsonSchema.addProperty(persistentProperty.getName(), property);
            }
        });

        Link link = entityLinks.linkToCollectionResource(persistentEntity.getType()).expand();

        LinkCollectingAssociationHandler associationHandler = new LinkCollectingAssociationHandler(repositories, new Path(
                link.getHref()), new AssociationLinks(mappings));
        persistentEntity.doWithAssociations(associationHandler);

        jsonSchema.add(associationHandler.getLinks());

        return jsonSchema;
    }


    private String resolveMessage(ResourceDescription description) {

        try {
            return accessor.getMessage(description);
        } catch (NoSuchMessageException o_O) {
            return description.getMessage();
        }
    }
}
