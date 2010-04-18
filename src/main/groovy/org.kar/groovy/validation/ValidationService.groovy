/*
 * Copyright (c) 2010.
 */



package org.kar.groovy.validation

import javax.validation.Configuration
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator

/**
 * Provides JSR-303 bean validation services using Closures to represent validation rules.
 * @author Kelly Robinson
 */
class ValidationService
{
    /**
     * Create a configuration object passing closures as validation mapping documents.
     * @param closures closures to render into validation mapping documents
     * @return config
     */
    public Configuration<?> createConfig(Closure... closures)
    {
        Configuration<?> config = Validation.byDefaultProvider().configure()
        closures.each {
            config.addMapping(new ByteArrayInputStream(GroovyXmlConversionUtil.convertToXml(it).bytes))
        }
        config
    }

    /**
     * Create a validator passing closures as validation mapping documents.
     * @param closures closures to render into validation mapping documents
     * @return validator
     */
    public Validator createValidator(Closure... closures)
    {
        createConfig(closures).buildValidatorFactory().validator
    }

    /**
     * Validate a given bean mapping the validation rules using the passed in closures.
     * @param bean
     * @param closures
     * @return
     */
    public Set<ConstraintViolation> validate(Object bean, Closure... closures)
    {
        createValidator(closures).validate(bean)
    }
}
