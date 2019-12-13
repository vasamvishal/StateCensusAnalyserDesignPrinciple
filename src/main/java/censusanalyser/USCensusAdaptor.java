package censusanalyser;

import java.util.Map;

public class USCensusAdaptor extends CommonAdaptar {

    @Override
    public <E> Map<String, CensussDAO> loadCensusData(CensusAnalyser.COUNTRY country, String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensussDAO> censusData = super.loadCensusData(USCensusCsv.class, csvFilePath);
        if(csvFilePath.length>1)
        {
            throw new CensusAnalyserException("Array out of bond",CensusAnalyserException.ExceptionType.ARRAY_OUT_OF_BOUND);
        }
        return censusData;
    }
}
