package com.expensetracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "files")
public class File {

    @Id
    @Column(name = "id")
    @GeneratedValue
    public Integer id;

    @Column(name = "url")
    public String url;

}
