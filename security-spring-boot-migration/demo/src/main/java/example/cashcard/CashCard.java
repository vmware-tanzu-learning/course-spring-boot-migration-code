package example.cashcard;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class CashCard {

    @Id private Long id;

    private Double amount;

    private String owner;

    public CashCard() {
    }

    public CashCard(Long id, Double amount, String owner) {
        this.id = id;
        this.amount = amount;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CashCard cashCard = (CashCard) o;

        if (!Objects.equals(id, cashCard.id)) return false;
        if (!Objects.equals(amount, cashCard.amount)) return false;
        return Objects.equals(owner, cashCard.owner);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
