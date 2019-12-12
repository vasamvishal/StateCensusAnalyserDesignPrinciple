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

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCsvIterator = csvBuilder.
                    getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> censusCsvIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).forEach(censusCsv -> censusStateMap.put(censusCsv.state, new CensussDAO(censusCsv)));
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), ExceptionType.HEADER_EXCEPTION);
        }
    }

    public int loadIndiaStateCode(String indiaStateCodeFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaStateCodeFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCsvIterator = csvBuilder.
                    getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> censusCsvIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState->censusStateMap.get(csvState.StateName) !=null)
                    .forEach(csvState->censusStateMap.get(csvState.StateName).StateCode=csvState.StateCode);
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    ExceptionType.NO_CENSUS_DATA);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), ExceptionType.HEADER_EXCEPTION);
        }
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

    public int loadUSCensusDataCode(String USCensusFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(USCensusFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusCsv> censusCsvIterator = csvBuilder.
                    getCSVFileIterator(reader, USCensusCsv.class);
            Iterable<USCensusCsv> csvIterable = () -> censusCsvIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).forEach(censusCsv -> censusStateMap.put(censusCsv.State, new CensussDAO(censusCsv)));
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), ExceptionType.HEADER_EXCEPTION);
        }
    }
}

