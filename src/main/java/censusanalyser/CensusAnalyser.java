package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static censusanalyser.CensusAnalyserException.*;

public class CensusAnalyser {

    public enum COUNTRY {
        INDIA, US
    }

    private COUNTRY country;
    Map<String, CensussDAO> censusStateMap = null;
    Map<FieldsToSort, Comparator<CensussDAO>> fields = null;

    public CensusAnalyser(COUNTRY country) {
        this.country = country;
        this.fields = new HashMap();
        this.fields.put(FieldsToSort.state, Comparator.comparing(census -> census.state));
        this.fields.put(FieldsToSort.population, Comparator.comparing(census ->
                census.population, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.areaInSqKm, Comparator.comparing(census ->
                census.TotalArea, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.densityPerSqKm, Comparator.comparing(census ->
                census.PopulationDensity, Comparator.reverseOrder()));
    }

    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
    }

    public Map<String, CensussDAO> loadCensusDataCode(COUNTRY country, String... csvFilePath) throws CensusAnalyserException {
        CommonAdaptar censusObject = CountryFactory.createObject(country);
        Map<String, CensussDAO> censusStateMap = censusObject.loadCensusData(country, csvFilePath);
        return censusStateMap;
    }

    public String getFieldWiseSortedCensusData(Map<String, CensussDAO> censusStateMap, FieldsToSort ... fieldsToSort) throws CensusAnalyserException {
        this.censusStateMap = censusStateMap;
        Comparator<CensussDAO> censussDAOComparator = null;
        if (this.censusStateMap.size() == 0 || this.censusStateMap == null) {
            throw new CensusAnalyserException("List is Empty", ExceptionType.NO_CENSUS_DATA);
        }
        if (fieldsToSort.length == 1) {
            censussDAOComparator = this.fields.get(fieldsToSort[0]);

        } else if (fieldsToSort.length == 2) {
           censussDAOComparator = this.fields.get(fieldsToSort[0])
                    .thenComparing(this.fields.get(fieldsToSort[1]));
        }
        List arrayList = censusStateMap.values().stream().
                sorted(censussDAOComparator).
                map(cencussDao -> cencussDao.getCencusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateData = new Gson().toJson(arrayList);
        return sortedStateData;
    }
    }


