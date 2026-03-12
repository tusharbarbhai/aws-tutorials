package com.codedude.springbootdynamodb.style;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class StyleReviewService {

    public StyleReviewResponse review(StyleReviewRequest request) {
        int score = computeScore(request);
        String verdict = buildVerdict(score);

        return StyleReviewResponse.builder()
                .score(score)
                .verdict(verdict)
                .generatedPreviewImageUrl(buildPreviewImageUrl(request))
                .explanation(buildExplanation(score, request))
                .strengths(buildStrengths(request, score))
                .suggestions(buildSuggestions(score))
                .build();
    }

    private int computeScore(StyleReviewRequest request) {
        int score = 50;

        if (hasText(request.getSkinTone())) {
            score += 8;
        }
        if (hasText(request.getBodyShape())) {
            score += 8;
        }
        if (hasText(request.getDressingStyle())) {
            score += 10;
        }
        if (hasText(request.getClothingName())) {
            score += 7;
        }

        if (request.getAge() != null) {
            if (request.getAge() >= 18 && request.getAge() <= 60) {
                score += 7;
            } else {
                score += 4;
            }
        }

        if (hasText(request.getUserPhotoUrl())) {
            score += 6;
        }

        if (hasText(request.getMarketplace())) {
            score += 4;
        }

        return Math.min(score, 100);
    }

    private String buildVerdict(int score) {
        if (score >= 85) {
            return "Excellent match";
        }
        if (score >= 70) {
            return "Good match";
        }
        if (score >= 55) {
            return "Average match";
        }
        return "Needs improvement";
    }

    private String buildPreviewImageUrl(StyleReviewRequest request) {
        String seed = (safeText(request.getUserPhotoUrl())
                + "|" + safeText(request.getClothingItemId())
                + "|" + safeText(request.getClothingName()))
                .replace(" ", "%20");
        return "https://image-preview.example.com/render?seed="
                + java.util.Base64.getUrlEncoder().withoutPadding()
                .encodeToString(seed.getBytes(StandardCharsets.UTF_8));
    }

    private String buildExplanation(int score, StyleReviewRequest request) {
        return "The style reviewer compared your profile factors (skin tone, body shape, age, dressing style) "
                + "with the selected clothing from " + safeText(request.getMarketplace())
                + " and generated a confidence score of " + score + "/100.";
    }

    private List<String> buildStrengths(StyleReviewRequest request, int score) {
        List<String> strengths = new ArrayList<>();

        if (hasText(request.getSkinTone())) {
            strengths.add("Color compatibility with your skin tone was considered.");
        }
        if (hasText(request.getBodyShape())) {
            strengths.add("Fit suitability for your body shape was factored in.");
        }
        if (hasText(request.getDressingStyle())) {
            strengths.add("Alignment with your preferred dressing style improved the score.");
        }
        if (score >= 80) {
            strengths.add("Overall outfit harmony is high for the selected item.");
        }

        if (strengths.isEmpty()) {
            strengths.add("Provide profile factors to unlock personalized strengths.");
        }

        return strengths;
    }

    private List<String> buildSuggestions(int score) {
        List<String> suggestions = new ArrayList<>();
        if (score >= 85) {
            suggestions.add("This pick works well; consider matching accessories for a complete look.");
        } else if (score >= 70) {
            suggestions.add("Try a slightly different color variant to further improve confidence.");
        } else {
            suggestions.add("Try different cuts and color tones for a better overall match.");
            suggestions.add("Add more profile details for better personalization.");
        }
        return suggestions;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String safeText(String value) {
        return hasText(value) ? value.trim() : "unknown";
    }
}
