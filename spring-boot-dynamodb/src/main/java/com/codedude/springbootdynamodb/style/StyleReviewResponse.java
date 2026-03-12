package com.codedude.springbootdynamodb.style;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StyleReviewResponse {
    private int score;
    private String verdict;
    private String generatedPreviewImageUrl;
    private String explanation;
    private List<String> strengths;
    private List<String> suggestions;
}
