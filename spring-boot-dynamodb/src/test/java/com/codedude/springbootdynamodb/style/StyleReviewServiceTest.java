package com.codedude.springbootdynamodb.style;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StyleReviewServiceTest {

    private final StyleReviewService styleReviewService = new StyleReviewService();

    @Test
    void shouldReturnHighScoreForCompleteProfile() {
        StyleReviewRequest request = new StyleReviewRequest();
        request.setUserPhotoUrl("https://example.com/user.jpg");
        request.setClothingItemId("ITEM-123");
        request.setClothingName("Slim Fit Navy Blazer");
        request.setMarketplace("StyleMart");
        request.setSkinTone("warm");
        request.setBodyShape("athletic");
        request.setAge(28);
        request.setDressingStyle("smart casual");

        StyleReviewResponse response = styleReviewService.review(request);

        assertTrue(response.getScore() >= 85);
        assertNotNull(response.getGeneratedPreviewImageUrl());
        assertTrue(response.getGeneratedPreviewImageUrl().contains("image-preview.example.com"));
    }
}
