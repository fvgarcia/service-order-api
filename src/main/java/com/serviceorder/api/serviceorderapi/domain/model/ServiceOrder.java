package com.serviceorder.api.serviceorderapi.domain.model;

import com.serviceorder.api.serviceorderapi.domain.exception.RuleException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    private String description;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ServiceOrderStatus status;

    private OffsetDateTime openingDate;
    private OffsetDateTime closingDate;

    @OneToMany(mappedBy = "serviceOrder")
    private List<Comment> comments = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ServiceOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceOrderStatus status) {
        this.status = status;
    }

    public OffsetDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(OffsetDateTime startDate) {
        this.openingDate = startDate;
    }

    public OffsetDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(OffsetDateTime endDate) {
        this.closingDate = endDate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceOrder that = (ServiceOrder) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean canBeCanceled(){
        return ServiceOrderStatus.OPEN.equals(getStatus());
    }

    public boolean cantBeCanceled(){
        return !canBeCanceled();
    }

    public boolean canBeClosed(){
        return ServiceOrderStatus.OPEN.equals(getStatus());
    }

    public boolean cantBeClosed(){
        return !canBeClosed();
    }

    public void close(){
        if (cantBeClosed()){
            throw new RuleException("Service order can't be closed");
        }
        setStatus(ServiceOrderStatus.CLOSED);
        setClosingDate(OffsetDateTime.now());
    }

    public void cancel(){
        if (cantBeCanceled()){
            throw new RuleException("Service order can't be canceled");
        }
        setStatus(ServiceOrderStatus.CANCELED);
        setClosingDate(OffsetDateTime.now());
    }
}

