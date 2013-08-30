package com.zju.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the basic "user" object in AppFuse that allows for authentication and user management. It
 * implements Acegi Security's UserDetails interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Updated by Dan Kibler (dan@getrolling.com) Extended
 *         to implement Acegi UserDetails interface by David Carter david@carter.net
 */
@Entity
@Table(name = "intlab_user")
@Searchable
@XmlRootElement
public class User extends BaseObject implements Serializable, UserDetails {
	private static final long serialVersionUID = 3832626162173359411L;

	private Long id;
	private String username; // required
	private String password; // required
	private String confirmPassword;
	private String passwordHint;
	private String firstName; // required
	private String lastName; // required
	private String email; // required; unique
	private String phoneNumber;
	private String website;
	private Address address = new Address();
	private Integer version;
	private Set<Role> roles = new HashSet<Role>();
	private Set<Rule> rules = new HashSet<Rule>();
	private String historyList;
	private boolean enabled;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;
	private String department;
	private String labCode;
	private String activeCode;
	private boolean activeAuto;
	private String lastLibrary;        //审核人员当天所在的部门
	private int writeBackCount;
	private String lastProfile;

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public User() {
	}
    
    
    private String knowledgebase;
    private String rulebase;
    private String reasoningResult;

	/**
	 * Create a new instance and set the username.
	 * 
	 * @param username
	 *            login name for user.
	 */
	public User(final String username) {
		this.username = username;
	}

	
	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
	@SequenceGenerator(name = "SEQ_USER", sequenceName = "user_sequence", allocationSize = 1)
	@SearchableId
	public Long getId() {
		return id;
	}

	@Column(nullable = false, length = 50, unique = true)
	@SearchableProperty
	public String getUsername() {
		return username;
	}

	@Column(nullable = false)
	@XmlTransient
	public String getPassword() {
		return password;
	}

	@Transient
	@XmlTransient
	public String getConfirmPassword() {
		return confirmPassword;
	}

	@Column(name = "password_hint")
	@XmlTransient
	public String getPasswordHint() {
		return passwordHint;
	}

	@Column(name = "first_name", nullable = false, length = 50)
	@SearchableProperty
	public String getFirstName() {
		return firstName;
	}

	@Column(name = "last_name", nullable = false, length = 50)
	@SearchableProperty
	public String getLastName() {
		return lastName;
	}

	@Column()
	@SearchableProperty
	public String getEmail() {
		return email;
	}

	@Column(name = "phone_number")
	@SearchableProperty
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@SearchableProperty
	public String getWebsite() {
		return website;
	}

	/**
	 * Returns the full name.
	 * 
	 * @return firstName + ' ' + lastName
	 */
	@Transient
	public String getFullName() {
		return firstName + ' ' + lastName;
	}

	@Embedded
	@SearchableComponent
	public Address getAddress() {
		return address;
	}

	/*@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "intlab_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}*/

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "intlab_user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<Role> getRoles() {
        return roles;
    }
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, targetEntity = Rule.class, mappedBy = "createUser")
	public Set<Rule> getRules() {
		return rules;
	}

	/**
	 * Convert user roles to LabelValue objects for convenience.
	 * 
	 * @return a list of LabelValue objects with role information
	 */
	@Transient
	public List<LabelValue> getRoleList() {
		List<LabelValue> userRoles = new ArrayList<LabelValue>();

		if (this.roles != null) {
			for (Role role : roles) {
				// convert the user's roles to LabelValue Objects
				userRoles.add(new LabelValue(role.getName(), role.getName()));
			}
		}

		return userRoles;
	}

	/**
	 * Adds a role for the user
	 * 
	 * @param role
	 *            the fully instantiated role
	 */
	public void addRole(Role role) {
		getRoles().add(role);
	}

	/**
	 * @return GrantedAuthority[] an array of roles.
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Transient
	public Set<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
		authorities.addAll(roles);
		return authorities;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	@Column(name = "account_enabled")
	public boolean isEnabled() {
		return enabled;
	}

	@Column(name = "account_expired", nullable = false)
	public boolean isAccountExpired() {
		return accountExpired;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 * @return true if account is still active
	 */
	@Transient
	public boolean isAccountNonExpired() {
		return !isAccountExpired();
	}

	@Column(name = "account_locked", nullable = false)
	public boolean isAccountLocked() {
		return accountLocked;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 * @return false if account is locked
	 */
	@Transient
	public boolean isAccountNonLocked() {
		return !isAccountLocked();
	}

	@Column(name = "credentials_expired", nullable = false)
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 * @return true if credentials haven't expired
	 */
	@Transient
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	@Column(name = "HISTORYLIST")
	public String getHistoryList() {
		return historyList;
	}

	public void setHistoryList(String historyList) {
		this.historyList = historyList;
	}

	@Column(name = "knowledgebase")
	public String getKnowledgebase() {
		return knowledgebase;
	}

	public void setKnowledgebase(String knowledgebase) {
		this.knowledgebase = knowledgebase;
	}

	@Column(name = "rulebase")
	public String getRulebase() {
		return rulebase;
	}

	public void setRulebase(String rulebase) {
		this.rulebase = rulebase;
	}

	@Column(name = "reasoning_result")
	public String getReasoningResult() {
		return reasoningResult;
	}

	public void setReasoningResult(String reasoningResult) {
		this.reasoningResult = reasoningResult;
	}
	
	@Column(name = "lastselectedprofile")
	public String getLastProfile() {
		return lastProfile;
	}


	public void setLastProfile(String lastProfile) {
		this.lastProfile = lastProfile;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}

		final User user = (User) o;

		return !(username != null ? !username.equals(user.getUsername()) : user.getUsername() != null);

	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (username != null ? username.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("username", this.username)
				.append("enabled", this.enabled).append("accountExpired", this.accountExpired)
				.append("credentialsExpired", this.credentialsExpired).append("accountLocked", this.accountLocked);

		if (roles != null) {
			sb.append("Granted Authorities: ");

			int i = 0;
			for (Role role : roles) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(role.toString());
				i++;
			}
		} else {
			sb.append("No Granted Authorities");
		}
		return sb.toString();
	}

	@Transient
	public String getRole() {
		String str = "";
		for (Role r : roles) {
			if (!str.equals(""))
				str += ",";
			String name = r.getName();
			RoleEnum role = (RoleEnum) Enum.valueOf(RoleEnum.class, name);

			switch (role) {
			case ROLE_ADMIN:
				str += "管理员";
				break;
			case ROLE_DOCTOR:
				str += "医生";
				break;
			case ROLE_OPERATOR:
				str += "检验人员";
				break;
			case ROLE_PATIENT:
				str += "患者";
				break;
			case ROLE_USER:
				str += "用户";
				break;

			default:
				break;
			}
		}
		return str;
	}
	
	@Transient
	public boolean isOperator() {
		for (Role r : roles) {
			String name = r.getName();
			if ("ROLE_OPERATOR".equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	@Transient
	public boolean isAdmin() {
		for (Role r : roles) {
			String name = r.getName();
			if ("ROLE_ADMIN".equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Column
	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "lab_code")
	public String getLabCode() {
		return labCode;
	}

	public void setLabCode(String labCode) {
		this.labCode = labCode;
	}
	
	@Column(name = "activecode")
	public String getActiveCode() {
		return activeCode;
	}


	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	
	@Column(name = "activeauto", nullable = false)
	public boolean isActiveAuto() {
		return activeAuto;
	}

	public void setActiveAuto(boolean activeAuto) {
		this.activeAuto = activeAuto;
	}

	@Column(name = "library")
	public String getLastLibrary() {
		return lastLibrary;
	}

	public void setLastLibrary(String lastLibrary) {
		this.lastLibrary = lastLibrary;
	}

	@Transient
	public int getWriteBackCount() {
		return writeBackCount;
	}

	public void setWriteBackCount(int writeBackCount) {
		this.writeBackCount = writeBackCount;
	}
}
