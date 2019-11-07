package au.org.consumerdatastandards.holder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import au.org.consumerdatastandards.holder.model.Meta;

@ApiModel
public class RequestAccountIds  {

    /**
     * Get data
     */
    private RequestAccountIdsData data;

    /**
     * Get meta
     */
    private Meta meta;

    public RequestAccountIds data(RequestAccountIdsData data) {
        this.data = data;
        return this;
    }

    @ApiModelProperty(required = true, value = "")
    public RequestAccountIdsData getData() {
        return data;
    }

    public void setData(RequestAccountIdsData data) {
        this.data = data;
    }
    public RequestAccountIds meta(Meta meta) {
        this.meta = meta;
        return this;
    }

    @ApiModelProperty(value = "")
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestAccountIds requestAccountIds = (RequestAccountIds) o;
        return Objects.equals(this.data, requestAccountIds.data) &&
            Objects.equals(this.meta, requestAccountIds.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            data,
            meta);
    }

    @Override
    public String toString() {
        return "class RequestAccountIds {\n" +
            "   data: " + toIndentedString(data) + "\n" + 
            "   meta: " + toIndentedString(meta) + "\n" + 
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

