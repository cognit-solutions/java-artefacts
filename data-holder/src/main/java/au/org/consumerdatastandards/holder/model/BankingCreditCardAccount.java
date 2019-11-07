package au.org.consumerdatastandards.holder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import java.time.LocalDate;

@ApiModel
public class BankingCreditCardAccount  {

    /**
     * The minimum payment amount due for the next card payment
     */
    private String minPaymentAmount;

    /**
     * If absent assumes AUD
     */
    private String paymentCurrency;

    /**
     * The amount due for the next card payment
     */
    private String paymentDueAmount;

    /**
     * Date that the next payment for the card is due
     */
    private LocalDate paymentDueDate;

    public BankingCreditCardAccount minPaymentAmount(String minPaymentAmount) {
        this.minPaymentAmount = minPaymentAmount;
        return this;
    }

    @ApiModelProperty(required = true, value = "The minimum payment amount due for the next card payment")
    public String getMinPaymentAmount() {
        return minPaymentAmount;
    }

    public void setMinPaymentAmount(String minPaymentAmount) {
        this.minPaymentAmount = minPaymentAmount;
    }
    public BankingCreditCardAccount paymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
        return this;
    }

    @ApiModelProperty(value = "If absent assumes AUD")
    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }
    public BankingCreditCardAccount paymentDueAmount(String paymentDueAmount) {
        this.paymentDueAmount = paymentDueAmount;
        return this;
    }

    @ApiModelProperty(required = true, value = "The amount due for the next card payment")
    public String getPaymentDueAmount() {
        return paymentDueAmount;
    }

    public void setPaymentDueAmount(String paymentDueAmount) {
        this.paymentDueAmount = paymentDueAmount;
    }
    public BankingCreditCardAccount paymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
        return this;
    }

    @ApiModelProperty(required = true, value = "Date that the next payment for the card is due")
    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankingCreditCardAccount bankingCreditCardAccount = (BankingCreditCardAccount) o;
        return Objects.equals(this.minPaymentAmount, bankingCreditCardAccount.minPaymentAmount) &&
            Objects.equals(this.paymentCurrency, bankingCreditCardAccount.paymentCurrency) &&
            Objects.equals(this.paymentDueAmount, bankingCreditCardAccount.paymentDueAmount) &&
            Objects.equals(this.paymentDueDate, bankingCreditCardAccount.paymentDueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            minPaymentAmount,
            paymentCurrency,
            paymentDueAmount,
            paymentDueDate);
    }

    @Override
    public String toString() {
        return "class BankingCreditCardAccount {\n" +
            "   minPaymentAmount: " + toIndentedString(minPaymentAmount) + "\n" + 
            "   paymentCurrency: " + toIndentedString(paymentCurrency) + "\n" + 
            "   paymentDueAmount: " + toIndentedString(paymentDueAmount) + "\n" + 
            "   paymentDueDate: " + toIndentedString(paymentDueDate) + "\n" + 
            "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

