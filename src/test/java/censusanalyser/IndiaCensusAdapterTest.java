package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndiaCensusAdapterTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
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

}

