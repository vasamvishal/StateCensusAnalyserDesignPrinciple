package censusanalyser;

public class CensussDAO {
    public String state;
    public String StateCode;
    public int population;
    public double TotalArea;
    public double PopulationDensity;

    public CensussDAO(IndiaCensusCSV indiaCensusCSV) {
        this.population=indiaCensusCSV.population;
        this.state=indiaCensusCSV.state;
        this.TotalArea=indiaCensusCSV.areaInSqKm;
        this.PopulationDensity=indiaCensusCSV.densityPerSqKm;
    }

    public CensussDAO(USCensusCsv censusCsv) {
        this.population=censusCsv.Population;
        this.state=censusCsv.State;
        this.TotalArea=censusCsv.TotalArea;
        this.PopulationDensity=censusCsv.PopulationDensity;

    }
    public Object getCencusDTO(CensusAnalyser.COUNTRY country)
    {
        if(country.equals(CensusAnalyser.COUNTRY.US))
            return new USCensusCsv(state,population,(int) PopulationDensity,TotalArea);
            return new IndiaCensusCSV(state,population,(int)PopulationDensity,(int)TotalArea);
    }
}
