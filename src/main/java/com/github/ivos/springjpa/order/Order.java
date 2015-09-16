package com.github.ivos.springjpa.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.github.ivos.springjpa.customer.Customer;

@Entity
@Table(name = "order_", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "orderNumber" }, name = "uc_order__orderNumber") })
@SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
	private Long id;

	@Version
	private long version;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date effectiveDate;

	@NotNull
	@Size(max = 10)
	private String orderNumber;

	@NotNull
	@ManyToOne(optional = false)
	// fetch = FetchType.LAZY
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_order__customer") )
	private Customer customer;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "order")
	// fetch = FetchType.EAGER
	@OrderBy("id")
	private List<OrderItem> orderItems = new ArrayList<>();

	public Order() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", version=" + version + ", effectiveDate=" + effectiveDate + ", orderNumber="
				+ orderNumber + ", customer=" + ((null == customer) ? null : customer.getId()) + "]";
	}

}
