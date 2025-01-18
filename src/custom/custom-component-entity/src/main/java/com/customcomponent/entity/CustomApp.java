package com.customcomponent.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SCO_APPS", uniqueConstraints = @UniqueConstraint(columnNames = {"DEVELOPER", "TENANT_ID", "NAME"}, name = "scp_apps_pk"))
@NamedQueries({
		@NamedQuery(
				name = CustomApp.FIND_BY_NAME_DEV, query = "SELECT sa FROM CustomApp sa WHERE sa.tenantId = :tenantId"
						+ " AND (LOWER(sa.name) LIKE :text OR LOWER(sa.developer) LIKE :text)",
				hints = {@QueryHint(name = "parameters", value = "java.lang.Long tenantId")
		}),
		@NamedQuery(
				name = CustomApp.FIND_BY_CATEGORY, query = "SELECT sa FROM CustomApp sa WHERE sa.tenantId = :tenantId"
						+ " AND sa.category.id = :categoryId",
				hints = {@QueryHint(name = "parameters", value = "java.lang.Long tenantId")
		}),
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomApp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_NAME_DEV = "CustomApp.findAll";	
	public static final String FIND_BY_CATEGORY = "CustomApp.findByCategory";
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@Id private Long id;

	@Column(name = "TENANT_ID")
	@NotNull private Long tenantId;
		
	@Column(name = "NAME", unique = true)
	@NotNull private String name;

	@Column(name = "DEVELOPER")	
	@NotNull private String developer;
	
	@Transient	
    private Long categoryId;

	@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID", updatable = true)
    @ManyToOne
    private CustomCategory category;
	
	public CustomApp(String name, String developer, Long categoryId) {
		this.name = name;
		this.developer = developer;
		this.categoryId = categoryId;
	}
}