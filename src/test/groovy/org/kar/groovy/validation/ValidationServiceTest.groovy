/*
 * Copyright (c) 2010.
 */







package org.kar.groovy.validation

import javax.validation.ConstraintViolation
import org.kar.test.objects.ValidateTestableClass

/**
 * @author Kelly Robinson
 */
class ValidationServiceTest extends GroovyTestCase
{
    /**
     * test validations against instances with different state
     */
    public void testValidation()
    {
        def service = new ValidationService()

        def invalid = new ValidateTestableClass() //should never validate
        def idBelowMinimum = new ValidateTestableClass(name: 'name', description: 'description', id: 1, enabled: true) //invalid id
        def enabledIsFalse = new ValidateTestableClass(name: 'name', description: 'description', id: 2, enabled: false) //invalid enabled
        def valid = new ValidateTestableClass(name: 'name', description: 'description', id: 2, enabled: true) //should always validate

        //violate all 3 rules
        def allErrors = service.validate(invalid, TestValidations.strictConstraint)
        assertEquals("Four validation failures expected but got instead: $allErrors", 4, allErrors.size())

        //violate id must be >=2
        List<ConstraintViolation> idMin = service.validate(idBelowMinimum, TestValidations.strictConstraint) as List
        assertEquals("Id is below minimum $idMin", 1, idMin.size())
        assertEquals(idMin[0].getInvalidValue(), 1)
        assertEquals("Loose constraint shouldn't care about id", 0, service.validate(idBelowMinimum, TestValidations.looseConstraint).size())

        //violate enabled == true
        List<ConstraintViolation> falseEnabled = service.validate(enabledIsFalse, TestValidations.strictConstraint) as List
        assertEquals("Expected enabled can't be false but got $falseEnabled", 1, falseEnabled.size())
        assertEquals(falseEnabled[0].getInvalidValue(), false)
        assertEquals("Loose constraint shouldn't care about enabled", 0, service.validate(enabledIsFalse, TestValidations.looseConstraint).size())

        //pass the valid instance
        def noErrors = service.validate(valid, TestValidations.strictConstraint)
        assertEquals("No problems expected but found $noErrors", 0, noErrors.size())
        noErrors = service.validate(valid, TestValidations.looseConstraint)
        assertEquals("Loose constraint obviously is satisfied if the strict constraint is", 0, noErrors.size())
    }
}
