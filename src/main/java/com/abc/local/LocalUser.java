package com.abc.local;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name="test")
public class LocalUser {
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Id
    @Column(name="ID", length = 40, nullable = false)
    private String id;

    @Column
    private String name;

    @Column
    private int age;
}
