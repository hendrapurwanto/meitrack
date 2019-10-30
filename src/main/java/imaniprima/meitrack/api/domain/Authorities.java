package imaniprima.meitrack.api.domain;


import org.springframework.beans.factory.annotation.Autowired;

public enum Authorities {
    SUPER_ADMIN(0,"SUPER ADMIN"),
    USER(1,"USER");

    public static String getById(Long id) {
        for(Authorities e : values()) {
            if(e.id.equals(id)) return e.name;
        }
        return "USER";
    }

    private String name;

    private Integer id;

    Authorities(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}

