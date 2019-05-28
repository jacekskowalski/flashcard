package com.project.flashcards.Repository;


import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "timestats")
@CrossOrigin
public class TimeStats implements Serializable {

    private static final long serialVersionUID = -1930333517297602205L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int countlogin;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Appuser appuser;
    public TimeStats() {
    }

    public int getCountlogin() {
        return countlogin;
    }

    public void setCountlogin(int countlogin) {
        this.countlogin = countlogin;
    }

    public TimeStats(int countlogin, double time) {
        this.countlogin = countlogin;
        }

    public TimeStats(int countlogin,  Appuser appuser) {
        this.countlogin = countlogin;
        this.appuser = appuser;
    }


    public Appuser getAppuser() {
        return appuser;
    }

    public void setAppuser(Appuser appuser) {
        this.appuser = appuser;
    }
}
