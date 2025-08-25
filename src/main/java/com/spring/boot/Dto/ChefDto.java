package com.spring.boot.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ChefDto", description = "Data Transfer Object for Chef")
public class ChefDto implements Serializable {
    @Schema(description = "Chef ID", example = "1")
    private Long id;

    @NotBlank(message = "chef.name.required")
    @Size(min = 2, max = 50, message = "chef.name.size")
    @Schema(description = "Name of the chef", example = "Ahmed Ramadan")
    private String name;

    @NotBlank(message = "chef.spec.required")
    @Size(min = 2, max = 100, message = "chef.spec.size")
    @Schema(description = "Specialization of the chef", example = "Italian Cuisine")
    private String spec;

    @NotBlank(message = "chef.logoPath.required")
    @Schema(description = "Logo path or URL of the chef", example = "https://example.com/chef-logo.png")
    private String logoPath;

    @NotBlank(message = "chef.facebook.link.required")
    @Pattern(regexp = "^(http|https)://.*$", message = "chef.facebook.link.invalid")
    @Schema(description = "Facebook profile link", example = "https://facebook.com/chefprofile")
    private String faceLink;

    @NotBlank(message = "chef.twitter.link.required")
    @Pattern(regexp = "^(http|https)://.*$", message = "chef.twitter.link.invalid")
    @Schema(description = "Twitter profile link", example = "https://twitter.com/chefprofile")
    private String tweLink;

    @NotBlank(message = "chef.instagram.link.required")
    @Pattern(regexp = "^(http|https)://.*$", message = "chef.instagram.link.invalid")
    @Schema(description = "Instagram profile link", example = "https://instagram.com/chefprofile")
    private String instaLink;
}
