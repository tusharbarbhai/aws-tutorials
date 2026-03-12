package com.codedude.springbootdynamodb.style;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/style-review")
public class StyleReviewController {

    @Autowired
    private StyleReviewService styleReviewService;

    @PostMapping
    public StyleReviewResponse review(@RequestBody StyleReviewRequest request) {
        return styleReviewService.review(request);
    }
}
