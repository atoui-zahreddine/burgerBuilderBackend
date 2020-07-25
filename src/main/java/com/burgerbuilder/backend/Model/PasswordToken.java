package com.burgerbuilder.backend.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    private long id;

    @Type(type="uuid-char")
    private UUID token;

    private Date expireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public PasswordToken(UUID token,Date expireDate, User user) {
        this.token = token;
        this.expireDate = expireDate;
        this.user = user;
    }
}
