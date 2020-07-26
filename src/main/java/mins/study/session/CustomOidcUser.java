package mins.study.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomOidcUser implements OidcUser, Serializable {

    private static final long serialVersionUID = 5986715161874403911L;

    private Map<String, Object> claims = new HashMap<>();
    private OidcUserInfo userInfo;
    private OidcIdToken idToken;
    private Map<String, Object> attributes = new HashMap<>();
    private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
    private String name;

    public CustomOidcUser(OidcUserRequest userRequest, Collection<? extends GrantedAuthority> authorities, String name) {
        this.idToken = userRequest.getIdToken();
        this.claims.putAll(this.idToken.getClaims());
        this.userInfo = new OidcUserInfo(this.claims);
        this.attributes = this.idToken.getClaims() != null ? this.idToken.getClaims() : this.userInfo.getClaims();
        this.authorities = authorities;
        this.name = name;
    }
}
