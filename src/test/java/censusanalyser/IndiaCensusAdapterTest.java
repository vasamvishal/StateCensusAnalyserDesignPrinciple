package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndiaCensusAdapterTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String INDIANCENSUSDATA_DELIMITER_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/WrongIndiaStateCensusData.csv";
    private static final String INDIANSTATE_WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String _INDIAN_STATE_CODE_DELIMITER_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/WrongIndiaStateCensusData.csv";

    @Test
    public void givenCSVFile_ShouldGive_SizeOfHashMap() {
        try {
            IndiaAdaptorSensor indiaAdaptorSensor = new IndiaAdaptorSensor();
            Map<String, CensussDAO> censusData = indiaAdaptorSensor.loadCensusData(CensusAnalyser.COUNTRY.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(29, censusData.size());
        } catch (CensusAnalyserException e) {
            e.getMessage();
        }
    }
    @Test
    public void givenWrongCSVFile_ShouldThrow_Exception() {
        try {
            IndiaAdaptorSensor indiaAdaptorSensor = new IndiaAdaptorSensor();
            Map<String, CensussDAO> censusData = indiaAdaptorSensor.loadCensusData(CensusAnalyser.COUNTRY.INDIA,INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(29, censusData.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenWrongDelimiterFile_ShouldThrow_Exception() {
        try {
            IndiaAdaptorSensor indiaAdaptorSensor = new IndiaAdaptorSensor();
            Map<String, CensussDAO> censusData = indiaAdaptorSensor.loadCensusData(CensusAnalyser.COUNTRY.INDIA,INDIANCENSUSDATA_DELIMITER_FILE_PATH);
            Assert.assertEquals(29, censusData.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION,e.type);
        }
    }

}

