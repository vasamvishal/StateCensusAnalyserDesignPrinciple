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
import static java.lang.StrictMath.E;

public class CensusAnalyser {
    Map<String, CensussDAO> censusStateMap = null;


    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath, USCensusCsv.class);
    }

    public int loadUSCensusDataCode(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath, IndiaCensusCSV.class);
    }

    private <E> int loadCensusData(String csvFilePath, Class<E> CensusCSVClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCsvIterator = csvBuilder.
                    getCSVFileIterator(reader, CensusCSVClass);
            Iterable<E> csvIterable = () -> censusCsvIterator;
            if (CensusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCsv -> censusStateMap.put(censusCsv.state, new CensussDAO(censusCsv)));

            } else if (CensusCSVClass.getName().equals("censusanalyser.USCensusCsv")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCsv.class::cast)
                        .forEach(censusCsv -> censusStateMap.put(censusCsv.State, new CensussDAO(censusCsv)));
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), ExceptionType.HEADER_EXCEPTION);
        }
        return censusStateMap.size();
    }


    public int loadIndiaStateCode(String indiaStateCodeFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaStateCodeFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCsvIterator = csvBuilder.
                    getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> censusCsvIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusStateMap.get(csvState.StateName) != null)
                    .forEach(csvState -> censusStateMap.get(csvState.StateName).StateCode = csvState.StateCode);
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
}


