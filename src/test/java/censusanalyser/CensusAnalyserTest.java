package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIANCENSUSDATA_DELIMITER_FILE_PATH="/home/user/vishalVasam/CensusAnalyser/src/test/resources/WrongIndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_FILE_PATH="/home/user/vishalVasam/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String INDIANSTATE_WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_ForWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfStateCode = censusAnalyser.loadIndiaStateCode(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(37, noOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_ForWrongDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfStateCode = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, noOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_ForWrongLimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfStateCode = censusAnalyser.loadIndiaStateCode(INDIANCENSUSDATA_DELIMITER_FILE_PATH);
            Assert.assertEquals(37, noOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_ForWrongCSVHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfStateCode = censusAnalyser.loadIndiaStateCode(INDIANCENSUSDATA_DELIMITER_FILE_PATH);
            Assert.assertEquals(37, noOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenIndianStateCode_ShouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfStateCode = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, noOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenIndianStateCode_ForWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfStateCode = censusAnalyser.loadIndiaStateCode(INDIANSTATE_WRONG_CSV_FILE_PATH);
            Assert.assertEquals(37, noOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenIndianStateCode_ForWrongClass_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfStateCode = censusAnalyser.loadIndiaStateCode(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(37, noOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenIndianData_WhenSortedOnState_ShouldReturnSortedOutput()  {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] CensusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", CensusCSV[0].state);
        }
        catch (CensusAnalyserException e) {

        }
    }
}
