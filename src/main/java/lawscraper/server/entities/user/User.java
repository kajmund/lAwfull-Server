package lawscraper.server.entities.user;

import lawscraper.server.entities.legalresearch.LegalResearch;
import lawscraper.server.entities.superclasses.EntityBase;
import lawscraper.shared.UserRole;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 4/16/12
 * Time: 6:43 PM
 */

@Entity
@Table(name = "user")
public class User extends EntityBase {
    String userName;
    String password;
    String eMail;
    Set<LegalResearch> legalResearchList = new HashSet<LegalResearch>();
    private UserRole userRole;
    private LegalResearch activeLegalResearch;

    public User() {
    }

    public User(String userName, String password) {
        setUserName(userName);
        setPassword(password);
    }

    public User(UserRole userRole) {
        this.setUserRole(userRole);
    }

    public User(String userName, String password, UserRole userRole) {
        setUserName(userName);
        setPassword(password);
        setUserRole(userRole);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<LegalResearch> getLegalResearch() {
        return legalResearchList;
    }

    public void setLegalResearch(Set<LegalResearch> legalResearchs) {
        this.legalResearchList = legalResearchs;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public static User AnonymousUser() {
        return new User(UserRole.Anonymous);
    }

    public static User AnonymousAdministratorUser() {
        User user = new User(UserRole.Administrator);
        user.setUserName("root");
        return user;
    }

    public boolean addLegalResearch(LegalResearch legalResearch) {
        for (LegalResearch research : legalResearchList) {
            if (legalResearch.getId() == research.getId()) {
                return false;
            }
        }

        getLegalResearch().add(legalResearch);
        legalResearch.setUser(this);

        return true;
    }

    public void setLegalResearchList(Set<LegalResearch> legalResearchList) {
        this.legalResearchList = legalResearchList;
    }

    @OneToOne
    public LegalResearch getActiveLegalResearch() {
        return activeLegalResearch;
    }

    public void setActiveLegalResearch(LegalResearch activeLegalResearch) {
        this.activeLegalResearch = activeLegalResearch;
    }
}
