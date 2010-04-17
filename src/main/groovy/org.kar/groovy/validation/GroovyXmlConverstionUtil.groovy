/*
 * Copyright (c) 2010.
 */

package org.kar.groovy.validation

import groovy.xml.StreamingMarkupBuilder
import org.codehaus.groovy.tools.xml.DomToGroovy

/**
 * @author Kelly Robinson
 */
class GroovyXmlConverstionUtil
{
    /**
     * Convert an xml file into the equivalent Groovy syntax
     * @param file a properly formatted xml file
     * @return the Groovy string required to build the xml file from scratch
     */
    public static String convertToGroovy(File file)
    {
        def writer = new StringWriter()
        def groovy = new DomToGroovy(new PrintWriter(writer))
        groovy.print(DomToGroovy.parse(file))
        return writer.toString()
    }

    /**
     * Evaluate a String into a Closure object and build xml from it.
     * @param closureString
     * @return
     */
    public static String convertToXml(String closureString)
    {
        return convertToXml(Eval.me("{ it -> $closureString }".toString()))
    }

    /**
     * Bind the closure to a StreamingMarkupBuilder and return the result.
     * @param closure nodes to turn into xml
     * @return xml as a String
     */
    public static String convertToXml(Closure closure)
    {
        Closure clone = closure.clone()
        def builder = new StreamingMarkupBuilder()
        clone.delegate = builder
        clone.setResolveStrategy(Closure.DELEGATE_ONLY)
        return builder.bind(clone).toString()
    }
}
