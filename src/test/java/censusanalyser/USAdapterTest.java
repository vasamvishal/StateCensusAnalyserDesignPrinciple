package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USAdapterTest {
    private static final String US_CENSUS_FILE_PATH = "/home/user/vishalVasam/CensusAnalyser/src/test/resources/USCensusData.csv";

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
}
