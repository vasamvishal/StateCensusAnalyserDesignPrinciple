package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCsv {

    @CsvBindByName(column = "State Id", required = true)
    public String StateId;

    @CsvBindByName(column = "State ", required = true)
    public String State;

    @CsvBindByName(column = "Population",required = true)
    public int Population;

    @CsvBindByName(column = "Total area",required = true)
    public double TotalArea;

    @CsvBindByName(column = "Population Density",required = true)
    public double PopulationDensity;

    public USCensusCsv() {
    }

    public USCensusCsv(String state, int population, int populationDensity, double totalArea) {
        this.State=state;
        this.Population=population;
        this.PopulationDensity=populationDensity;
        this.TotalArea=totalArea;
    }
}
