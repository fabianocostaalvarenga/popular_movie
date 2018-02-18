package com.udacity.filmesfamosos.model.dto;

import com.udacity.filmesfamosos.model.ReviewModel;
import com.udacity.filmesfamosos.model.TrailerModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fabiano.alvarenga on 18/02/18.
 */

public class DetailsResultTaskDTO implements Serializable {

    private List<TrailerModel> trailerModelList;
    private List<ReviewModel> reviewModels;

    public DetailsResultTaskDTO(List<TrailerModel> trailerModelList, List<ReviewModel> reviewModels) {
        this.trailerModelList = trailerModelList;
        this.reviewModels = reviewModels;
    }

    public List<TrailerModel> getTrailerModelList() {
        return trailerModelList;
    }

    public void setTrailerModelList(List<TrailerModel> trailerModelList) {
        this.trailerModelList = trailerModelList;
    }

    public List<ReviewModel> getReviewModels() {
        return reviewModels;
    }

    public void setReviewModels(List<ReviewModel> reviewModels) {
        this.reviewModels = reviewModels;
    }

}
