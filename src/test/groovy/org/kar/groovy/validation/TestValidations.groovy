/*
 * Copyright (c) 2010.
 */







package org.kar.groovy.validation

/**
 * declarations of
 * @author Kelly Robinson
 */
class TestValidations
{
    def static strictConstraint = {
        mkp.declareNamespace(xsi: 'http://www.w3.org/2001/XMLSchema-instance')
        'constraint-mappings'(xmlns: 'http://jboss.org/xml/ns/javax/validation/mapping',
                'xsi:schemaLocation': 'http://jboss.org/xml/ns/javax/validation/mapping validation-mapping-1.0.xsd') {
            'default-package'('org.kar.test.objects')
            bean('class': 'ValidateTestableClass') {
                ['name', 'description'].each {
                    field(name: it) {
                        constraint(annotation: 'javax.validation.constraints.NotNull')
                    }
                }
                field(name: 'id') {
                    constraint(annotation: 'javax.validation.constraints.DecimalMin') {
                        element(name: 'value', '2')
                    }
                }
                field(name: 'enabled') {
                    constraint(annotation: 'javax.validation.constraints.AssertTrue')
                }
            }
        }
    }

    def static looseConstraint = {
        mkp.declareNamespace(xsi: 'http://www.w3.org/2001/XMLSchema-instance')
        'constraint-mappings'(xmlns: 'http://jboss.org/xml/ns/javax/validation/mapping',
                'xsi:schemaLocation': 'http://jboss.org/xml/ns/javax/validation/mapping validation-mapping-1.0.xsd') {
            'default-package'('org.kar.test.objects')
            bean('class': 'ValidateTestableClass') {
                field(name: 'name') {
                    constraint(annotation: 'javax.validation.constraints.NotNull')
                }
            }
        }
    }
}
