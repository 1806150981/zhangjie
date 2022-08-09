package com.icss.demo.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liu jun
 */
@Entity
@Table(name = "MaXiangYu")
@Data
public class MaXiangYu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;


}
