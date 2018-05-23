package ba.unsa.etf.nwt.authservice.models;

import org.springframework.http.HttpMethod;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class AccessPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private WebResource resource;

    @NotNull
    private HttpMethod httpMethod;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public AccessPermission() { }

    public AccessPermission(@NotNull WebResource resource, @NotNull HttpMethod httpMethod) {
        this.resource = resource;
        this.httpMethod = httpMethod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WebResource getResource() {
        return resource;
    }

    public void setResource(WebResource resource) {
        this.resource = resource;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

}
