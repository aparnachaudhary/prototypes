package net.arunoday;

import com.fasterxml.jackson.databind.ObjectMapper;
import oi.thekraken.grok.api.Grok;
import oi.thekraken.grok.api.Match;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

/**
 * Test for extracting date field.
 * <p/>
 * Created by Aparna on 11/15/14.
 */
public class DateExtractorTest {

    @Test
    public void testDate() throws Exception {

        Grok grok = Grok.EMPTY;

        grok.addPattern("WORD", "\\b\\w+\\b");
        grok.addPattern("GREEDYDATA", ".*");
        grok.addPattern("YEAR", "(?>\\d\\d){1,2}");
        grok.addPattern("MONTHNUM", "(?:0?[1-9]|1[0-2])");
        grok.addPattern("MONTHDAY", "(?:(?:0[1-9])|(?:[12][0-9])|(?:3[01])|[1-9])");
        grok.addPattern("HOUR", "(?:2[0123]|[01]?[0-9])");
        grok.addPattern("MINUTE", "(?:[0-5][0-9])");
        grok.addPattern("SECOND", "(?:(?:[0-5]?[0-9]|60)(?:[:.,][0-9]+)?)");
        grok.addPattern("ISO8601_TIMEZONE", "(?:Z|[+-]%{HOUR}(?::?%{MINUTE}))");
        grok.addPattern("MY_HOUR", "(2[0123]|[01]?[0-9])");
        grok.addPattern("MY_MINUTE", "([0-5][0-9])");
        grok.addPattern("MY_SECOND", "((?:[0-5]?[0-9]|60)(?:[:.,][0-9]+)?)");
        grok.addPattern("startDate", "%{YEAR}-%{MONTHNUM}-%{MONTHDAY}[T ]%{MY_HOUR}_%{MY_MINUTE}_%{MY_SECOND}?%{ISO8601_TIMEZONE}?");

        // compile and add semantic
        grok.compile("%{WORD:employeeId}/%{GREEDYDATA:empName}/%{startDate}");

        String inputData = "E1234/Aparna/Chaudhary/2014-1-15T13_02_49+01:00";
        Match match = grok.match(inputData);
        match.captures();

        // extract parsed data
        HashMap<String, String> resultData = new ObjectMapper().readValue(match.toJson(), HashMap.class);

        assertEquals("invalid employee id", "E1234", resultData.get("employeeId"));
        assertEquals("invalid name", "Aparna/Chaudhary", resultData.get("empName"));
        assertEquals("invalid date", "2014-1-15T13_02_49+01:00", resultData.get("startDate"));

    }
}
