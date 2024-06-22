package com.zaroyan.librarymanagerexjdbc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zaroyan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    private Long id;
    private String title;
    private String author;
    private int publicationYear;
}
