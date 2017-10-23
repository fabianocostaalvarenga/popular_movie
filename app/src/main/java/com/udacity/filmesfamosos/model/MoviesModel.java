package com.udacity.filmesfamosos.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by fabiano.alvarenga on 10/15/17.
 */

public class MoviesModel implements Serializable {

    private Boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("belongs_to_collection")
    private List<BelongsModel> belongs;

    private BigDecimal budget;

    @SerializedName("genre_ids")
    private List<GenresModel> genres;

    private String homepage;

    private Long id;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    private String overview;

    private BigDecimal popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private List<ProdutctionCompaniesModel> produtctionCompanies;

    @SerializedName("production_countries")
    private List<ProductionCountriesModel> productionCountries;

    @SerializedName("release_date")
    private Date releaseDate;

    private Long revenue;

    private Long runtime;

    @SerializedName("spoken_languages")
    private List<SpokenLanguages> spokenLanguages;

    private String status;

    private String tagline;

    private Boolean video;

    @SerializedName("vote_average")
    private BigDecimal voteAverage;

    @SerializedName("vote_count")
    private BigDecimal voteCounte;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<BelongsModel> getBelongs() {
        return belongs;
    }

    public void setBelongs(List<BelongsModel> belongs) {
        this.belongs = belongs;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public List<GenresModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresModel> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public BigDecimal getPopularity() {
        return popularity;
    }

    public void setPopularity(BigDecimal popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ProdutctionCompaniesModel> getProdutctionCompanies() {
        return produtctionCompanies;
    }

    public void setProdutctionCompanies(List<ProdutctionCompaniesModel> produtctionCompanies) {
        this.produtctionCompanies = produtctionCompanies;
    }

    public List<ProductionCountriesModel> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountriesModel> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public List<SpokenLanguages> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguages> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public BigDecimal getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(BigDecimal voteAverage) {
        this.voteAverage = voteAverage;
    }

    public BigDecimal getVoteCounte() {
        return voteCounte;
    }

    public void setVoteCounte(BigDecimal voteCounte) {
        this.voteCounte = voteCounte;
    }

}
