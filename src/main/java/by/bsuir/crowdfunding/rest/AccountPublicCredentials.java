package by.bsuir.crowdfunding.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountPublicCredentials
{
    private final String kid;
    private final String b64UrlPublicKey;

    @JsonCreator
    public AccountPublicCredentials(@JsonProperty("kid") String kid,
                                    @JsonProperty("b64UrlPublicKey") String b64UrlPublicKey)
    {
        this.kid = kid;
        this.b64UrlPublicKey = b64UrlPublicKey;
    }

    public String getKid()
    {
        return kid;
    }

    public String getB64UrlPublicKey()
    {
        return b64UrlPublicKey;
    }
}
