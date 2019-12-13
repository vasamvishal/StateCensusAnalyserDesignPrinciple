package censusanalyser;

import java.util.Map;

public class IndiaAdaptorSensor extends CommonAdaptar {
    @Override
    public <E> Map<String, CensussDAO> loadCensusData(CensusAnalyser.COUNTRY country, String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensussDAO> censusData = this.loadCensusData(IndiaCensusCSV.class, csvFilePath);
        try {
            this.loadIndiaStateCode(censusData, csvFilePath[1]);
        } catch (ArrayIndexOutOfBoundsException e){
            throw new CensusAnalyserException("Array index out of bond",CensusAnalyserException.ExceptionType.ARRAY_OUT_OF_BOUND);
        }
        return censusData;
    }
}



