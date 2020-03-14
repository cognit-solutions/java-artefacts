/*
 * Consumer Data Standards
 * Sample CLI tool to Demonstrate the Consumer Data Right APIs
 *
 * NOTE: This class is auto generated by the codegen artefact
 * https://github.com/ConsumerDataStandardsAustralia/java-artefacts/codegen
 */
package au.org.consumerdatastandards.client.cli;

import au.org.consumerdatastandards.client.ApiResponse;
import au.org.consumerdatastandards.client.ConformanceError;
import au.org.consumerdatastandards.client.Header;
import au.org.consumerdatastandards.client.cli.support.ApiClientOptions;
import au.org.consumerdatastandards.client.model.BaseResponse;
import au.org.consumerdatastandards.client.model.Links;
import au.org.consumerdatastandards.client.model.LinksPaginated;
import au.org.consumerdatastandards.client.model.MetaPaginated;
import au.org.consumerdatastandards.client.model.PaginatedResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiCliBase {

    @Autowired
    protected ApiClientOptions apiClientOptions;

    protected void throwConformanceErrors(List<ConformanceError> conformanceErrors) throws Exception {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("Received " + conformanceErrors.size() + " errors");
        for (ConformanceError conformanceError : conformanceErrors) {
            pw.println("Conformance Error: " + conformanceError.getDescription());
        }
        pw.println("Found a total of " + conformanceErrors.size() + " conformance errors");
        throw new Exception(sw.toString());
    }

    protected int getEndpointVersion(ApiResponse response) {
        Map headers = response.getHeaders();
        List<String> values = (List<String>) headers.get(Header.VERSION.getKey());
        if (values != null && !values.isEmpty()) {
            String version = values.get(0);
            if(StringUtils.isNumeric(version)) {
                return Integer.parseInt(version);
            }
        }
        return 1;
    }

    public List<ConformanceError> validateMetadata(String requestUrl, Object response) {
        Integer page = 1, pageSize = 25;
        Map<String, Object> params = extractParameters(requestUrl);
        List<ConformanceError> errors = new ArrayList<>();
        try {
            Integer pageParam = getPageParameter(params);
            if (pageParam != null) page = pageParam;
        } catch (NumberFormatException e) {
            errors.add(new ConformanceError()
                    .errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage("Invalid 'page' parameter value " + getParameter(params, "page"))
            );
        }
        try {
            Integer pageSizeParam = getPageSizeParameter(params);
            if (pageSizeParam != null) pageSize = pageSizeParam;
        } catch (NumberFormatException e) {
            errors.add(new ConformanceError()
                    .errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage("Invalid 'page-size' parameter value " + getParameter(params, "page-size"))
            );
        }

        Integer totalPages = null;
        if (response instanceof PaginatedResponse) {
            MetaPaginated meta = ((PaginatedResponse)response).getMeta();
            if (meta != null) {
                Integer totalRecords = meta.getTotalRecords();
                totalPages = meta.getTotalPages();
                if (totalRecords != null && totalPages != null
                        && (totalRecords / pageSize + (totalRecords % pageSize > 0 ? 1 : 0)) != totalPages) {
                    errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                            .errorMessage(String.format(
                                    "totalPages %d does not match totalRecords / page-size + remainder of (totalRecords / page-size), "
                                            + "where page-size is " + pageSize + " . See below:\n%s", totalPages, meta.toString()))
                    );
                }
            }
        }
        Links links = (response instanceof PaginatedResponse ? ((PaginatedResponse)response).getLinks() : ((BaseResponse)response).getLinks());
        if (links != null) {
            String self = links.getSelf();
            String linksJson = toJson(links);
            checkSelfLink(requestUrl, errors, linksJson, self);
            if (links instanceof LinksPaginated) {
                String first = ((LinksPaginated) links).getFirst();
                String prev = ((LinksPaginated) links).getPrev();
                String next = ((LinksPaginated) links).getNext();
                String last = ((LinksPaginated) links).getLast();
                checkFirstLink(page, pageSize, errors, totalPages, linksJson, first);
                checkLastLink(page, pageSize, errors, totalPages, linksJson, last);
                checkPrevLink(page, errors, linksJson, prev);
                checkNextLink(page, errors, totalPages, linksJson, next);
            }
        }
        return errors;
    }

    private Map<String, Object> extractParameters(String url) {
        String[] parts = url.split("\\?");
        if (parts.length < 2) return null;
        String queryString = parts[1];
        Map<String, Object> params = new HashMap<>();
        String[] queryParams = queryString.split("&");
        for (String queryParam : queryParams) {
            String[] keyValue = queryParam.split("=");
            String key = keyValue[0];
            Object value = keyValue.length > 1 ? keyValue[1] : Boolean.TRUE;
            if (params.containsKey(key)) {
                if (params.get(key) instanceof List) {
                    ((List) params.get(key)).add(value);
                } else {
                    List<Object> valueList = new ArrayList<>();
                    valueList.add(params.get(key));
                    valueList.add(value);
                }
            } else {
                params.put(key, value);
            }
        }
        return params;
    }

    private Integer getIntParameter(Map<String, Object> params, String paramName) {
        String s = getParameter(params, paramName);
        if (StringUtils.isBlank(s)) {
            return null;
        }
        return Integer.parseInt(s);
    }

    private String getParameter(Map<String, Object> params, String paramName) {
        if (params == null || params.get(paramName) == null) {
            return null;
        }
        return params.get(paramName).toString();
    }

    private Integer getPageParameter(Map<String, Object> params) {
        return getIntParameter(params, "page");
    }

    private Integer getPageSizeParameter(Map<String, Object> params) {
        return getIntParameter(params, "page-size");
    }

    public static ObjectMapper createObjectMapper() {
        return new ObjectMapper().registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module()).registerModule(new JavaTimeModule())
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String toJson(Object dataObject) {
        try {
            return createObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(dataObject);
        } catch (JsonProcessingException e) {
            return dataObject.toString();
        }
    }

    private void checkFirstLink(Integer page, Integer pageSize, List<ConformanceError> errors, Integer totalPages, String linksJson, String first) {
        if (StringUtils.isBlank(first) && totalPages != null && totalPages > 0 && page != 1) {
            errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage(String.format("first link data is missing given totalPages %d in meta. See below:\n%s",
                            totalPages, linksJson))
            );
        } else if (totalPages != null && totalPages == 0 && !StringUtils.isBlank(first)) {
            errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage(String.format("There should be no first link given totalPages %s in meta. See below:\n%s",
                            totalPages, linksJson))
            );
        } else if (!StringUtils.isBlank(first)) {
            Map<String, Object> firstLinkParams = extractParameters(first);
            String firstLinkPageParam = getParameter(firstLinkParams, "page");
            if (!"1".equals(firstLinkPageParam)) {
                errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                        .errorMessage(String.format("first link %s does not have page param value as 1. See below:\n%s",
                                first, linksJson))
                );
            }
            String firstLinkPageSizeParam = getParameter(firstLinkParams, "page-size");
            if (!pageSize.toString().equals(firstLinkPageSizeParam)) {
                errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                        .errorMessage(String.format("first link %s page-size param value %s does not match request page-size %s. See below:\n%s",
                                first, firstLinkPageSizeParam, pageSize, linksJson))
                );
            }
        }
    }

    private void checkLastLink(Integer page, Integer pageSize, List<ConformanceError> errors, Integer totalPages, String linksJson, String last) {
        if (StringUtils.isBlank(last) && totalPages != null && totalPages > 0 && !page.equals(totalPages)) {
            errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage(String.format("last link data is missing given totalPages %d in meta. See below:\n%s",
                            totalPages, linksJson))
            );
        } else if (totalPages != null && totalPages == 0 && !StringUtils.isBlank(last)) {
            errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage(String.format("There should be no last link given totalPages %s in meta. See below:\n%s",
                            totalPages, linksJson))
            );
        } else if (!StringUtils.isBlank(last)) {
            Map<String, Object> lastLinkParams = extractParameters(last);
            String lastLinkPageParam = getParameter(lastLinkParams, "page");
            if (StringUtils.isBlank(lastLinkPageParam)) {
                errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                        .errorMessage(String.format("last link %s does not have page param. See below:\n%s",
                                last, linksJson))
                );
            } else {
                try {
                    Integer lastLinkPage = Integer.parseInt(lastLinkPageParam);
                    if (lastLinkPage < page) {
                        errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                                .errorMessage(String.format("last link %s have invalid page param %s. See below:\n%s",
                                        last, lastLinkPage, linksJson))
                        );
                    }
                } catch (NumberFormatException e) {
                    errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                            .dataJson(linksJson)
                            .errorMessage(String.format("last link %s does not have page param", last))
                    );
                }
            }
            String lastLinkPageSizeParam = getParameter(lastLinkParams, "page-size");
            if (!pageSize.toString().equals(lastLinkPageSizeParam)) {
                errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                        .errorMessage(String.format("last link %s page-size param value %s does not match request page-size %s. See below:\n%s",
                                last, lastLinkPageSizeParam, pageSize, linksJson))
                );
            }
        }
    }

    private void checkSelfLink(String requestUrl, List<ConformanceError> errors, String linksJson, String self) {
        if (!requestUrl.equals(self)) {
            errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage(String.format("Self %s does not match request url %s. See below:\n%s",
                            self, requestUrl, linksJson))
            );
        }
    }

    private void checkPrevLink(Integer page, List<ConformanceError> errors, String linksJson, String prev) {
        if (page == 1 && !StringUtils.isBlank(prev)) {
            errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage(String.format("Prev %s should be null as current page is the first page. See below:\n%s", prev, linksJson))
            );
        } else if (page > 1 && StringUtils.isBlank(prev)) {
            errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage(String.format("Prev %s should be not null as current page is not the first page. See below:\n%s", prev, linksJson))
            );
        } else if (page > 1 && !StringUtils.isBlank(prev)) {
            Map<String, Object> prevLinkParams = extractParameters(prev);
            String prevLinkPageParam = getParameter(prevLinkParams, "page");
            if (StringUtils.isBlank(prevLinkPageParam)) {
                errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                        .errorMessage(String.format("prev link %s does not have page param. See below:\n%s",
                                prev, linksJson))
                );
            } else {
                try {
                    int prevLinkPage = Integer.parseInt(prevLinkPageParam);
                    if (prevLinkPage != page - 1 ) {
                        errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                                .errorMessage(String.format("prev link %s have invalid page param %s. See below:\n%s",
                                        prev, prevLinkPage, linksJson))
                        );
                    }
                } catch (NumberFormatException e) {
                    errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                            .dataJson(linksJson)
                            .errorMessage(String.format("prev link %s does not have page param", prev))
                    );
                }
            }
        }
    }

    private void checkNextLink(Integer page, List<ConformanceError> errors, Integer totalPages, String linksJson, String next) {
        if (page.equals(totalPages) && !StringUtils.isBlank(next)) {
            errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage(String.format("Next %s should be null as current page is the last page. See below:\n%s", next, linksJson))
            );
        } else if (page < totalPages && StringUtils.isBlank(next)) {
            errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                    .errorMessage(String.format("Next %s should be not null as current page is not the last page. See below:\n%s", next, linksJson))
            );
        } else if (page < totalPages && !StringUtils.isBlank(next)) {
            Map<String, Object> nextLinkParams = extractParameters(next);
            String nextLinkPageParam = getParameter(nextLinkParams, "page");
            if (StringUtils.isBlank(nextLinkPageParam)) {
                errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                        .errorMessage(String.format("next link %s does not have page param. See below:\n%s",
                                next, linksJson))
                );
            } else {
                try {
                    int nextLinkPage = Integer.parseInt(nextLinkPageParam);
                    if (nextLinkPage != page + 1 ) {
                        errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                                .errorMessage(String.format("next link %s have invalid page param %s. See below:\n%s",
                                        next, nextLinkPage, linksJson))
                        );
                    }
                } catch (NumberFormatException e) {
                    errors.add(new ConformanceError().errorType(ConformanceError.Type.DATA_NOT_MATCHING_CRITERIA)
                            .dataJson(linksJson)
                            .errorMessage(String.format("next link %s does not have page param", next))
                    );
                }
            }
        }
    }
}
