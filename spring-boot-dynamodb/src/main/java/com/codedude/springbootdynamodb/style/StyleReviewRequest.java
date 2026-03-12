package com.codedude.springbootdynamodb.style;

import lombok.Data;

@Data
public class StyleReviewRequest {
    private String userPhotoUrl;
    private String clothingItemId;
    private String clothingName;
    private String marketplace;
    private String skinTone;
    private String bodyShape;
    private Integer age;
    private String dressingStyle;
}
