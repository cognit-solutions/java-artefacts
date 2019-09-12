package au.org.consumerdatastandards.api.banking.models;

import au.org.consumerdatastandards.support.data.*;

@DataDefinition(
    allOf = { BankingPayee.class },
    anyOf = { "domestic", "biller", "international" }
)
@CustomAttributes({
    @CustomAttribute(name = "x-conditional", value = "domestic", multiple = true),
    @CustomAttribute(name = "x-conditional", value = "biller", multiple = true),
    @CustomAttribute(name = "x-conditional", value = "international", multiple = true)
})
public class BankingPayeeDetail {

    public enum PayeeUType {
        domestic,
        biller,
        international
    }

    @Property(
        description = "Type of object included that describes the payee in detail",
        required = true
    )
    PayeeUType payeeUType;

    @Property(
        requiredIf = { @Condition(propertyName = "payeeUType", values = {"domestic"}) }
    )
    BankingDomesticPayee domestic;

    @Property(
        requiredIf = { @Condition(propertyName = "payeeUType", values = {"biller"}) }
    )
    BankingBillerPayee biller;

    @Property(
        requiredIf = { @Condition(propertyName = "payeeUType", values = {"international"}) }
    )
    BankingInternationalPayee international;
}
