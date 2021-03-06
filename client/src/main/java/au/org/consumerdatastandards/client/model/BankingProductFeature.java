/*
 * Consumer Data Standards
 * Sample client library to Demonstrate the Consumer Data Right APIs
 *
 * NOTE: This class is auto generated by the codegen artefact
 * https://github.com/ConsumerDataStandardsAustralia/java-artefacts/codegen
 */
package au.org.consumerdatastandards.client.model;

import java.util.Objects;

public class BankingProductFeature {

    public enum FeatureType {
        CARD_ACCESS,
        ADDITIONAL_CARDS,
        UNLIMITED_TXNS,
        FREE_TXNS,
        FREE_TXNS_ALLOWANCE,
        LOYALTY_PROGRAM,
        OFFSET,
        OVERDRAFT,
        REDRAW,
        INSURANCE,
        BALANCE_TRANSFERS,
        INTEREST_FREE,
        INTEREST_FREE_TRANSFERS,
        DIGITAL_WALLET,
        DIGITAL_BANKING,
        NPP_PAYID,
        NPP_ENABLED,
        DONATE_INTEREST,
        BILL_PAYMENT,
        COMPLEMENTARY_PRODUCT_DISCOUNTS,
        BONUS_REWARDS,
        NOTIFICATIONS,
        OTHER
    }

    private FeatureType featureType;

    private String additionalValue;

    private String additionalInfo;

    private String additionalInfoUri;

    /**
     * The type of feature described
     * @return featureType
     */
    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    /**
     * Generic field containing additional information relevant to the [featureType](#tocSproductfeaturetypedoc) specified. Whether mandatory or not is dependent on the value of the [featureType.](#tocSproductfeaturetypedoc)
     * @return additionalValue
     */
    public String getAdditionalValue() {
        return additionalValue;
    }

    public void setAdditionalValue(String additionalValue) {
        this.additionalValue = additionalValue;
    }

    /**
     * Display text providing more information on the feature. Mandatory if the [feature type](#tocSproductfeaturetypedoc) is set to OTHER
     * @return additionalInfo
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * Link to a web page with more information on this feature
     * @return additionalInfoUri
     */
    public String getAdditionalInfoUri() {
        return additionalInfoUri;
    }

    public void setAdditionalInfoUri(String additionalInfoUri) {
        this.additionalInfoUri = additionalInfoUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankingProductFeature bankingProductFeature = (BankingProductFeature) o;
        return Objects.equals(this.featureType, bankingProductFeature.featureType) &&
            Objects.equals(this.additionalValue, bankingProductFeature.additionalValue) &&
            Objects.equals(this.additionalInfo, bankingProductFeature.additionalInfo) &&
            Objects.equals(this.additionalInfoUri, bankingProductFeature.additionalInfoUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            featureType,
            additionalValue,
            additionalInfo,
            additionalInfoUri);
    }

    @Override
    public String toString() {
        return "class BankingProductFeature {\n" +
            "   featureType: " + toIndentedString(featureType) + "\n" + 
            "   additionalValue: " + toIndentedString(additionalValue) + "\n" + 
            "   additionalInfo: " + toIndentedString(additionalInfo) + "\n" + 
            "   additionalInfoUri: " + toIndentedString(additionalInfoUri) + "\n" + 
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
