package ba.unsa.etf.nwt.authservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class WebResource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String relativePath;

    @OneToMany(mappedBy = "resource")
    private List<AccessPermission> permissions;

    public WebResource() { }

    public WebResource(@NotBlank String name, @NotBlank String relativePath) {
        this.name = name;
        this.relativePath = relativePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
