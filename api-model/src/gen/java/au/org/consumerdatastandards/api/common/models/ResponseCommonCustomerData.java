package au.org.consumerdatastandards.api.common.models;

import au.org.consumerdatastandards.support.data.*;

@DataDefinition(
    referenced = false
)
public class ResponseCommonCustomerData {

    public enum CustomerUType {
        person,
        organisation
    }

    @Property(
        description = "The type of customer object that is present",
        required = true
    )
    CustomerUType customerUType;

    @Property(
        requiredIf = { @Condition(propertyName = "customerUType", values = {"person"}) }
    )
    CommonPerson person;

    @Property(
        requiredIf = { @Condition(propertyName = "customerUType", values = {"organisation"}) }
    )
    CommonOrganisation organisation;
}
