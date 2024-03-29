package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USAdapterTest {
    private static final String US_CENSUS_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/USCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaState/CensusData.csv";
    private static final String US_DELIMITER_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/UScensusDataDelimiter.csv";
    private static final String INDIA_STATE_CODE_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenUsfile_ShouldReturnHashmapSize() {
        try {
            USCensusAdaptor usCensusAdaptor = new USCensusAdaptor();
            Map<String, CensussDAO> censusData = usCensusAdaptor.loadCensusData(CensusAnalyser.COUNTRY.US, US_CENSUS_FILE_PATH);
            Assert.assertEquals(51, censusData.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongUsfile_ShouldReturnException() {
        try {
            USCensusAdaptor usCensusAdaptor = new USCensusAdaptor();
            Map<String, CensussDAO> censusData = usCensusAdaptor.loadCensusData(CensusAnalyser.COUNTRY.US, WRONG_CSV_FILE_PATH);
            Assert.assertEquals(51, censusData.size());
        } catch (CensusAnalyserException e) {
           Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenWrongDelimiterUsfile_ShouldReturnException() {
        try {
            USCensusAdaptor usCensusAdaptor = new USCensusAdaptor();
            Map<String, CensussDAO> censusData = usCensusAdaptor.loadCensusData(CensusAnalyser.COUNTRY.US, US_DELIMITER_FILE_PATH);
            Assert.assertEquals(51, censusData.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION,e.type);
        }
    }
    @Test
    public void givenWrongUsfile_forWrongClass_ShouldReturnException() {
        try {
            USCensusAdaptor usCensusAdaptor = new USCensusAdaptor();
            Map<String, CensussDAO> censusData = usCensusAdaptor.loadCensusData(CensusAnalyser.COUNTRY.US, INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(51, censusData.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION,e.type);
        }
    }
    @Test
    public void givenTwoUsfile_ShouldReturnException() {
        try {
            USCensusAdaptor usCensusAdaptor = new USCensusAdaptor();
            Map<String, CensussDAO> censusData = usCensusAdaptor.loadCensusData(CensusAnalyser.COUNTRY.US, US_CENSUS_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(51, censusData.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.ARRAY_OUT_OF_BOUND, e.type);
        }
    }

    @Test
    public void givenTwoUsfile_InReverse_ShouldReturnException() {
        try {
            USCensusAdaptor usCensusAdaptor = new USCensusAdaptor();
            Map<String, CensussDAO> censusData = usCensusAdaptor.loadCensusData(CensusAnalyser.COUNTRY.US, INDIA_STATE_CODE_FILE_PATH, US_CENSUS_FILE_PATH);
            Assert.assertEquals(51, censusData.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION, e.type);
        }
    }
}
