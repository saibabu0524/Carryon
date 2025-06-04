package com.saibabui.main.data

import com.saibabui.ui.Template

class TemplateApiServiceImpl : TemplateApiService {
    override fun getTemplates(): List<Template> {
        // This is a stub implementation. Replace with actual data fetching logic.
        return templateList
    }
}

// 5 Template objects
val templateList = listOf(
    Template(
        id = "1",
        name = "Modern Professional Resume",
        category = "Professional",
        previewImage = "https://example.com/modern_professional.png",
        completionPercentage = 85,
        description = "A sleek and modern resume template perfect for corporate professionals and business executives.",
        tags = listOf("modern", "professional", "corporate", "clean", "executive")
    ),
    Template(
        id = "2",
        name = "Creative Designer Portfolio",
        category = "Creative",
        previewImage = "https://example.com/creative_portfolio.png",
        completionPercentage = 60,
        description = "A vibrant and artistic template designed for graphic designers, artists, and creative professionals.",
        tags = listOf("creative", "artistic", "colorful", "portfolio", "design")
    ),
    Template(
        id = "3",
        name = "Tech Developer Resume",
        category = "Technology",
        previewImage = "https://example.com/tech_developer.png",
        completionPercentage = 92,
        description = "A technical resume template optimized for software developers, engineers, and IT professionals.",
        tags = listOf("tech", "developer", "programming", "engineering", "skills-focused")
    ),
    Template(
        id = "4",
        name = "Academic Research CV",
        category = "Academic",
        previewImage = "https://example.com/academic_cv.png",
        completionPercentage = 40,
        description = "A comprehensive CV template for researchers, professors, and PhD candidates with focus on publications and research.",
        tags = listOf("academic", "research", "education", "publications", "scholarly")
    ),
    Template(
        id = "5",
        name = "Minimalist Simple Resume",
        category = "Minimalist",
        previewImage = "https://example.com/minimalist_simple.png",
        completionPercentage = 100,
        description = "A clean and simple resume template that focuses on content with minimal design distractions.",
        tags = listOf("minimalist", "simple", "clean", "readable", "basic")
    )
)