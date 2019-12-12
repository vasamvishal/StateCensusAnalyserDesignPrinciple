package censusanalyser;

import com.google.gson.Gson;
import csvbuilderanalyser.CSVBuilderException;
import csvbuilderanalyser.CSVBuilderFactory;
import csvbuilderanalyser.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static censusanalyser.CensusAnalyserException.*;

public class CensusAnalyser {
    Map<String, CensussDAO> censusStateMap = null;


    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String ... csvFilePath) throws CensusAnalyserException {
        Map<String, CensussDAO> censusStateMap= new censusLoader().loadCensusData(IndiaCensusCSV.class,csvFilePath);
        return censusStateMap.size();
    }

    public int loadUSCensusDataCode(String ... csvFilePath) throws CensusAnalyserException {
        Map<String, CensussDAO> censusStateMap = new censusLoader().loadCensusData(USCensusCsv.class,csvFilePath);
        return censusStateMap.size();
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap.size() == 0 || censusStateMap == null) {
            throw new CensusAnalyserException("List is Empty", ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensussDAO> censusCSVComparator = Comparator.comparing(census -> census.state);
        List<CensussDAO> censussDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        List<CensussDAO> censussDAOS1 = this.sort(censussDAOS, censusCSVComparator);
        String sortedStateData = new Gson().toJson(censussDAOS1);
        return sortedStateData;
    }

    public String getStateWisePopulationforSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap.size() == 0 || censusStateMap == null) {
            throw new CensusAnalyserException("List is Empty", ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensussDAO> censusCSVComparator = Comparator.comparing(census -> census.population, Comparator.reverseOrder());
        List<CensussDAO> censussDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        List<CensussDAO> censussDAOS1 = this.sort(censussDAOS, censusCSVComparator);
        String sortedStateData = new Gson().toJson(censussDAOS1);
        return sortedStateData;
    }

    private List<CensussDAO> sort(List<CensussDAO> censussDAOS, Comparator<CensussDAO> censusCSVComparator) {
        for (int i = 0; i < censussDAOS.size() - 1; i++) {
            for (int j = 0; j < censussDAOS.size() - i - 1; j++) {
                CensussDAO census1 = censussDAOS.get(j);
                CensussDAO census2 = censussDAOS.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censussDAOS.set(j, census2);
                    censussDAOS.set(j + 1, census1);
                }
            }
        }
        return censussDAOS;
    }
}


