/*
 * Copyright (c) 2010.
 */



package org.kar.groovy.validation

import org.kar.groovy.validation.GroovyXmlConversionUtil as util

import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.XMLUnit

/**
 * @author Kelly Robinson
 */
class GroovyXmlConversionUtilTest extends GroovyTestCase
{
    /**
     * Read in an xml document, turn it into Groovy, convert it back and ensure the two documents match.
     */
    public void testConversionRoundtrip()
    {
        new File('src/test/resources/xml').eachFile {File file ->
            def groovyString = util.convertToGroovy(file)
            def xml = util.convertToXml(groovyString)
            XMLUnit.setIgnoreWhitespace(true)
            assertTrue("results should be identical", new Diff(file.text, xml).identical())
        }
    }
}
