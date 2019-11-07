package au.org.consumerdatastandards.holder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import java.time.LocalDate;

@ApiModel
public class BankingTermDepositAccount  {

    /**
     * The lodgement date of the original deposit
     */
    private LocalDate lodgementDate;

    /**
     * Amount to be paid upon maturity. If absent it implies the amount to paid is variable and cannot currently be calculated
     */
    private String maturityAmount;

    /**
     * If absent assumes AUD
     */
    private String maturityCurrency;

    /**
     * Maturity date for the term deposit
     */
    private LocalDate maturityDate;

    public enum MaturityInstructions {
        PAID_OUT_AT_MATURITY,
        ROLLED_OVER
    }
    /**
     * Get maturityInstructions
     */
    private MaturityInstructions maturityInstructions;

    public BankingTermDepositAccount lodgementDate(LocalDate lodgementDate) {
        this.lodgementDate = lodgementDate;
        return this;
    }

    @ApiModelProperty(required = true, value = "The lodgement date of the original deposit")
    public LocalDate getLodgementDate() {
        return lodgementDate;
    }

    public void setLodgementDate(LocalDate lodgementDate) {
        this.lodgementDate = lodgementDate;
    }
    public BankingTermDepositAccount maturityAmount(String maturityAmount) {
        this.maturityAmount = maturityAmount;
        return this;
    }

    @ApiModelProperty(value = "Amount to be paid upon maturity. If absent it implies the amount to paid is variable and cannot currently be calculated")
    public String getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(String maturityAmount) {
        this.maturityAmount = maturityAmount;
    }
    public BankingTermDepositAccount maturityCurrency(String maturityCurrency) {
        this.maturityCurrency = maturityCurrency;
        return this;
    }

    @ApiModelProperty(value = "If absent assumes AUD")
    public String getMaturityCurrency() {
        return maturityCurrency;
    }

    public void setMaturityCurrency(String maturityCurrency) {
        this.maturityCurrency = maturityCurrency;
    }
    public BankingTermDepositAccount maturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
        return this;
    }

    @ApiModelProperty(required = true, value = "Maturity date for the term deposit")
    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }
    public BankingTermDepositAccount maturityInstructions(MaturityInstructions maturityInstructions) {
        this.maturityInstructions = maturityInstructions;
        return this;
    }

    @ApiModelProperty(required = true, value = "")
    public MaturityInstructions getMaturityInstructions() {
        return maturityInstructions;
    }

    public void setMaturityInstructions(MaturityInstructions maturityInstructions) {
        this.maturityInstructions = maturityInstructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankingTermDepositAccount bankingTermDepositAccount = (BankingTermDepositAccount) o;
        return Objects.equals(this.lodgementDate, bankingTermDepositAccount.lodgementDate) &&
            Objects.equals(this.maturityAmount, bankingTermDepositAccount.maturityAmount) &&
            Objects.equals(this.maturityCurrency, bankingTermDepositAccount.maturityCurrency) &&
            Objects.equals(this.maturityDate, bankingTermDepositAccount.maturityDate) &&
            Objects.equals(this.maturityInstructions, bankingTermDepositAccount.maturityInstructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            lodgementDate,
            maturityAmount,
            maturityCurrency,
            maturityDate,
            maturityInstructions);
    }

    @Override
    public String toString() {
        return "class BankingTermDepositAccount {\n" +
            "   lodgementDate: " + toIndentedString(lodgementDate) + "\n" + 
            "   maturityAmount: " + toIndentedString(maturityAmount) + "\n" + 
            "   maturityCurrency: " + toIndentedString(maturityCurrency) + "\n" + 
            "   maturityDate: " + toIndentedString(maturityDate) + "\n" + 
            "   maturityInstructions: " + toIndentedString(maturityInstructions) + "\n" + 
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

