package censusanalyser;

import java.util.Map;

public class IndiaAdaptorSensor extends CommonAdaptar {
    @Override
    public <E> Map<String, CensussDAO> loadCensusData(CensusAnalyser.COUNTRY country, String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensussDAO> censusData = this.loadCensusData(IndiaCensusCSV.class, csvFilePath);
        return censusData;
    }
}



