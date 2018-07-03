package com.mahersoua.popularmovies.models;

import java.util.List;

class MainMovieModel {

    private int[] genreIds;
    private List<GenreModel> genres;
    private List<MovieCollectionModel> belongsToCollection;
    private List<ProductionCompaniesModel> productionCompanies;
    private List<ProductionCountryModel> productionCountries;
    private List<SpokenLanguageModel> spokenLanguages;
}
