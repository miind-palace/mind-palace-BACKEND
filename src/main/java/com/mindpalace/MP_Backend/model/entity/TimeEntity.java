package com.mindpalace.MP_Backend.model.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class TimeEntity { //시간정보 분리, 만들기 쉽게
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
