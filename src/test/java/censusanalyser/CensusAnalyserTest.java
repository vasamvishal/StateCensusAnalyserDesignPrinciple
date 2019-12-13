package censusanalyser;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaState/CensusData.csv";
    private static final String INDIANCENSUSDATA_DELIMITER_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/WrongIndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String INDIANSTATE_WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String _INDIAN_STATE_CODE_DELIMITER_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/WrongIndiaStateCensusData.csv";
    private static final String US_CENSUS_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, stringCensussDAOMap);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_ForWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, stringCensussDAOMap);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_ForWrongDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, stringCensussDAOMap);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_ForWrongLimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIANCENSUSDATA_DELIMITER_FILE_PATH);
            Assert.assertEquals(37, stringCensussDAOMap);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_ForWrongCSVHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIANCENSUSDATA_DELIMITER_FILE_PATH);
            Assert.assertEquals(37, stringCensussDAOMap);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianStateCode_ShouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(29, stringCensussDAOMap.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCode_ForWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIANSTATE_WRONG_CSV_FILE_PATH);
            Assert.assertEquals(37, stringCensussDAOMap);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIndianStateCode_ForWrongClass_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, stringCensussDAOMap);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianStateCode_ForImproperDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, _INDIAN_STATE_CODE_DELIMITER_FILE_PATH);
            Assert.assertEquals(37, stringCensussDAOMap);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianStateCode_ForImproperHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, _INDIAN_STATE_CODE_DELIMITER_FILE_PATH);
            Assert.assertEquals(37, stringCensussDAOMap.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianData_WhenSortedOnState_ShouldReturnSortedOutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.COUNTRY.INDIA);
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(stringCensussDAOMap,FieldsToSort.state);
            IndiaCensusCSV[] CensusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", CensusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIndianData_WhenSortedOnPopulation_ShouldReturnSortedOutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.COUNTRY.INDIA);
            Map<String, CensussDAO> censusStateMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(censusStateMap, FieldsToSort.population);
            IndiaCensusCSV[] CensusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", CensusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndianData_WhenSortedOnDensityperSqKm_ShouldReturnSortedOutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.COUNTRY.INDIA);
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(stringCensussDAOMap,FieldsToSort.densityPerSqKm);
            IndiaCensusCSV[] CensusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", CensusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenUsCensusData_ShouldReturnCensusData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.US, US_CENSUS_FILE_PATH);
            Assert.assertEquals(51, stringCensussDAOMap.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenEnumCountry_ShouldReturnHashMapSize() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensussDAO> stringCensussDAOMap = censusAnalyser.loadCensusDataCode(CensusAnalyser.COUNTRY.US, US_CENSUS_FILE_PATH);
            Assert.assertEquals(51, stringCensussDAOMap.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }
}
