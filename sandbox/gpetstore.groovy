#!/usr/bin/env groovy
/**
 * ====================================================================
 * Author Jim LoVerde
 * Copyright 2009 NVISIA, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 *
 * OK, so this isn't a full version of the "Pet Store" application.  In fact,
 * it's really only the domain model with a very simple servlet to render a 
 * few simple actions from the PetStore.  But it serves it's purpose as a 
 * proof of concept for how you could use GORM embedded in a single Groovy 
 * script.  And as a proof of concept, this script uses some Spring classes
 * in ways that they weren't necessarily intended, so it may not be the most
 * elegant solution, but it works.
 *
 * And as a bonus, this script registers the GSP servlet so that any GSP files
 * in the directory where you run this script can be rendered.  One can 
 * imagine grabbing the Spring ApplicationContext inside of those GSPs
 * and creating a GSP based full implementation of the PetStore.
 *
 * Or maybe some day finding a way to pull the Grails Controller plumbing out
 * so that it could run "standalone" and you could actually write controllers
 * in this script...
 *
 * NOTE: If running behind a firewall, you need to specify the http.proxyHost
 *       and http.proxyPort system properties for Grape to download the 
 *       dependencies: 
 *          groovy -Dhttp.proxyHost=yourproxy -Dhttp.proxyPort=8080 gpetstore.groovy  
 */
package gpetstore

@Grab(group = 'org.springframework', module = 'spring', version = '2.5.6')
@Grab(group = 'org.springframework', module = 'spring-web', version = '2.5.6')
@Grab(group = 'hsqldb', module = 'hsqldb', version = '1.8.0.7')
@Grab(group = 'org.hibernate', module = 'hibernate-core', version = '3.3.1.GA')
@Grab(group = 'org.hibernate', module = 'hibernate-annotations', version = '3.3.1.GA')
@Grab(group = 'org.grails', module = 'grails-core', version = '1.1')
@Grab(group = 'org.grails', module = 'grails-gorm', version = '1.1')
@Grab(group = 'org.grails', module = 'grails-bootstrap', version = '1.1')
@Grab(group = 'org.grails', module = 'grails-web', version = '1.1')
@Grab(group = 'javassist', module = 'javassist', version = '3.8.0.GA')
@Grab(group = 'org.mortbay.jetty', module = 'jetty-embedded', version = '6.1.0')
@Grab(group = 'commons-lang', module = 'commons-lang', version = '2.4')
class MyAppContext extends org.springframework.context.support.ClassPathXmlApplicationContext {
	protected org.springframework.core.io.Resource[] getConfigResources() {
		def config = """<?xml version="1.0" encoding="UTF-8"?>		
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:gorm="http://grails.org/schema/gorm" 
	xmlns:util="http://www.springframework.org/schema/util"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xsi:schemaLocation="
		http://www.springframework.org/schema/context 
		http://www.springframework.org/schema/context/spring-context-2.5.xsd 
		http://www.springframework.org/schema/beans 
		http://www.springframework.org/schema/beans/spring-beans.xsd 
		http://grails.org/schema/gorm 
		http://grails.org/schema/gorm/gorm.xsd 
		http://www.springframework.org/schema/util 
		http://www.springframework.org/schema/util/spring-util-2.0.xsd 
		http://www.springframework.org/schema/tx 
		http://www.springframework.org/schema/tx/spring-tx-2.5.xsd"
	default-lazy-init="false">

	<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		<property name="driverClassName" value="org.hsqldb.jdbcDriver" />
		<!--
		  - Use this URL instead of the mem url if you want the data to be stored in a file
		<property name="url" value="jdbc:hsqldb:file:gpetstoredb" />
		-->
		<property name="url" value="jdbc:hsqldb:mem:gpetstoredb" />
		<property name="username" value="sa" />
		<property name="password" value="" />
	</bean>

    <bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
        <property name="basename" value="messages" />
    </bean>

	<context:annotation-config/>
	
	<tx:annotation-driven/>

	<gorm:sessionFactory base-package="gpetstore" 
		data-source-ref="dataSource" 
	    message-source-ref="messageSource"> 
	    <property name="hibernateProperties"> 
	        <util:map> 
	            <entry key="hibernate.hbm2ddl.auto" value="update"/> 
	        </util:map> 
	    </property> 
	</gorm:sessionFactory>

	<!-- 
		Ideally we would be finished with the Spring configuration right here and
		the above gorm:sessionFactory definition would automatically scan the classpath
		and register our domain objects, including the ones defined in this script.

		However, the gorm:sessionFactory above currently uses the Spring classpath
		scanning logic which requires for there to be an actual .class file in the 
		classpath, so it doesn't find classes dynamically created by this script.
		
		So for now we have to explicitly override the definition of grailsApplication
		and explicitly enumerate each domain class.  Also, for each domain class we have
		to manually define the bean, domain bean, and validator bean.
		
		Hopefully GORM will handle this for us in the future and we can remove all of the 
		Spring configuration below.
	  -->
	${manualGormConfig}
</beans>
		"""
		return (org.springframework.core.io.Resource[]) [ new org.springframework.core.io.ByteArrayResource(config.getBytes()) ]
	}

	// As stated in the applicationContext.xml above, none of the rest of this code would
	// be needed if the gorm:sessionFactory definition could read GORM resources from the 
	// runtime Groovy classpath, however, for now we have to explicitly list them and
	// use the getManualGormConfig method to expand them into bean definitions
	def entities = [ 
		gpetstore.Account.class,
		gpetstore.Cart.class,
		gpetstore.CartItem.class,
		gpetstore.Category.class,
		gpetstore.Inventory.class,
		gpetstore.Item.class,
		gpetstore.LineItem.class,
		gpetstore.Orders.class,
		gpetstore.OrderStatus.class,
		gpetstore.Product.class,
		gpetstore.Supplier.class,
	]
	
	public String getManualGormConfig() {
		def manualConfig = """
		<bean id="grailsApplication" class="org.codehaus.groovy.grails.commons.DefaultGrailsApplication" init-method="initialise">
			<constructor-arg>
				<list>
			"""

		entities.each { entity -> manualConfig += "<value>${entity.name}</value>" }
		
		manualConfig += """
				</list>
			</constructor-arg>
			<constructor-arg>
				<bean class="groovy.lang.GroovyClassLoader" />
			</constructor-arg>
		</bean>
		"""
		
		entities.each { entity ->
			manualConfig += """
	    <bean id="${entity.name}" class="${entity.name}" scope="prototype" />
		<bean id="${entity.name}Domain" class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
			<property name="targetObject" ref="grailsApplication" />
			<property name="targetMethod" value="getArtefact" />
			<property name="arguments">
				<list>
					<value>Domain</value>	
					<value>${entity.name}</value>
				</list>
			</property>
		</bean>
		<bean id="${entity.name}Validator" class="org.codehaus.groovy.grails.orm.hibernate.validation.HibernateDomainClassValidator">
			<property name="messageSource" ref="messageSource" />
			<property name="domainClass" ref="${entity.name}Domain" />
		</bean>	
			"""
		}
		return manualConfig	
	}
}

@grails.persistence.Entity
class Account {
	static embedded = [ 'address' ]
 	String username
 	String password
 	String email
 	String firstName
 	String lastName
 	String status
	Address address
 	String phone
    Category favouriteCategory
 	String languagePreference
 	boolean listOption
 	boolean bannerOption
 	String bannerName
}

class Address {
 	String street1
 	String street2
 	String city
 	String state
 	String zip
 	String country
}

@grails.persistence.Entity
class Cart {
	static hasMany = [ items : CartItem ]
}

import org.apache.commons.lang.builder.*
@grails.persistence.Entity
class CartItem {
	Item item
 	int quantity
   	boolean inStock
	BigDecimal total
	public boolean equals(Object obj) { return EqualsBuilder.reflectionEquals(this, obj) }
	public int hashCode() { return HashCodeBuilder.reflectionHashCode(this) }
}

@grails.persistence.Entity
class Category {
	static hasMany = [ products : Product ]
 	String name
 	String description
}

@grails.persistence.Entity
class Inventory {
	Item item
	int quantity
}

@grails.persistence.Entity
class Item {
	Product product
 	BigDecimal listPrice
 	BigDecimal unitCost
 	Supplier supplier
 	String status
 	String attribute1
 	String attribute2
 	String attribute3
 	String attribute4
 	String attribute5
}

@grails.persistence.Entity
class LineItem {	
	static belongsTo = Orders
 	int lineNumber
 	Item item
 	int quantity
 	BigDecimal unitPrice
 	BigDecimal total
}

@grails.persistence.Entity
class Orders {
	static mapping
	static hasMany = [ lineItems : LineItem ]
	static embedded = [ 'shipAddress', 'billAddress']
 	String username
 	Date orderDate
 	String courier
 	BigDecimal totalPrice
	Address shipAddress
	Address billAddress
 	String billToFirstName
 	String billToLastName
 	String shipToFirstName
 	String shipToLastName
 	String creditCard
 	String expiryDate
 	String cardType
 	String locale
 	String status
}

@grails.persistence.Entity
class OrderStatus {
	LineItem lineItem
	java.sql.Timestamp timestamp
	String status
}

@grails.persistence.Entity
class Product {
	static belongsTo = Category
 	Category category
 	String name
 	String description
}

@grails.persistence.Entity
class Supplier {
	static embedded = [ 'address' ]
	String name
	String status
	Address address
	String phone
}


@Grab(group = 'org.slf4j', module = 'slf4j-api', version = '1.4.2')
@Grab(group = 'org.slf4j', module = 'slf4j-log4j12', version = '1.4.2')
def runServer(port) {
	org.apache.log4j.Logger.getRootLogger().getAllAppenders().each { it.setLayout(new org.apache.log4j.PatternLayout("%r [%t] %-5p %c %x - %m%n")) }
	org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO)
	org.apache.log4j.Logger.getLogger("org.hibernate.SQL").setLevel(org.apache.log4j.Level.DEBUG)
	
	// Spring setup, we need to explicitly call refresh to force the inline
	// XML configuration above to be loaded
	def appContext = new MyAppContext()
	appContext.refresh()
	
	// GORM Demo, load some boostrap data

	// Suppliers
	def abc = new Supplier(name:"ABC Pets", status:"AC", 
		address:new Address(street1:"600 Avon Way", street2:"", city:"Los Angeles", state:"CA", zip:"94024", country:"US"), 
		phone:"212-947-0797").save()
	def xyz = new Supplier(name:"XYZ Pets", status:"AC", 
		address:new Address(street1:"700 Abalone Way", street2:"", city:"San Francisco", state:"CA", zip:"94024", country:"US"), 
		phone:"415-947-0797").save()
	
	// Categories
	def fish = new Category(name: 'Fish', description: 'Things that swim').save()	
	def dogs = new Category(name: 'Dogs', description: 'Woof woof').save()	
	
	// Products
	def angelfish = new Product(category:fish, name:'AngelFish', description:'Salt Water fish from Australia').save()
	def goldfish = new Product(category:fish, name:'GoldFish', description:'Fresh Water fish from China').save()
	def bulldog = new Product(category:dogs, name:'Bulldog', description:'Friendly dog from England').save()
	def poodle = new Product(category:dogs, name:'Poodle', description:'Cute dog from France').save()
	
	// Items
	def item1 = new Item(product:angelfish, listPrice:16.50, unitCost:10.00, supplier:abc, status:'P', attribute1:'Large', attribute2:"", attribute3:"", attribute4:"", attribute5:"" ).save()
	def item2 = new Item(product:goldfish, listPrice:16.50, unitCost:10.00, supplier:abc, status:'P', attribute1:'Large', attribute2:"", attribute3:"", attribute4:"", attribute5:"" ).save()
	def item3 = new Item(product:bulldog, listPrice:16.50, unitCost:10.00, supplier:abc, status:'P', attribute1:'Large', attribute2:"", attribute3:"", attribute4:"", attribute5:"" ).save()
	def item4 = new Item(product:poodle, listPrice:16.50, unitCost:10.00, supplier:abc, status:'P', attribute1:'Large', attribute2:"", attribute3:"", attribute4:"", attribute5:"" ).save()
	
	// Inventory
	new Inventory(item:item1, quantity:10).save()	
	new Inventory(item:item2, quantity:10).save()	
	new Inventory(item:item3, quantity:10).save()	
	new Inventory(item:item4, quantity:10).save()	
	
	// Jetty setup with GSP support, Groovlet support, and a custom servlet
    def server = new org.mortbay.jetty.Server(port)
    def context = new org.mortbay.jetty.servlet.Context(server, "/", org.mortbay.jetty.servlet.Context.SESSIONS);
    context.resourceBase = "."
    context.setAttribute("appContext", appContext)
	context.addServlet(MyServlet, "/")
    context.addServlet(groovy.servlet.TemplateServlet, "*.gsp")
    //context.addServlet(groovy.servlet.GrooovyServlet, "*.groovy")
    server.start()
}

import javax.servlet.*
import javax.servlet.http.*
class MyServlet extends groovy.servlet.AbstractHttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, java.io.IOException {
		doPost(req, resp)
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, java.io.IOException {
		def action = req.getRequestURI().replaceAll("/", "")
		if (action == "") { action = "index" }
		if (!action.endsWith(".ico")) {
			this."$action"(req, resp)
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND)
		}
	}

	protected String getHeader() {
		return """
			[<a href="/">Home</a>] [<a href="/viewCart">View Cart</a>]
			"""
	}

	protected void index(req, resp) { index(req, resp, null) }
	
	protected void index(req, resp, msg) {
		resp.writer.print "<html><body>${header}"
		if (msg) { resp.writer.print msg }
		resp.writer.print "<h1>Product Categories</h1><ul>"
		Category.findAll().each { resp.writer.print "<li><a href=\"viewCategory?id=${it.id}\">${it.name}</a></li>" }
		resp.writer.print "</ul>"
		resp.writer.print "</body></html>"
	}
	
	protected void viewCategory(req, resp) {
		def id = req.getParameter('id')
		if (!id) { resp.sendRedirect("/"); return; }
		Category.withTransaction {
			def category = Category.get(id)
			resp.writer.print "<html><body>${header}"
			resp.writer.print "<h1>Products in Category: ${category.name}</h1><ul>"
			category.products.each { resp.writer.print "<li><a href=\"viewProduct?id=${it.id}\">${it.name}</a></li>" }
			resp.writer.print "</body></html>"
		}
	}

	protected void viewProduct(req, resp) {
		def id = req.getParameter('id')
		if (!id) { resp.sendRedirect("/"); return; }
		Item.withTransaction {
			def product = Product.get(id)
			def items = Item.findByProduct(product)
			resp.writer.print "<html><body>${header}"
			resp.writer.print "<h1>Items for Product: ${product.name}</h1><ul>"
			items.each { resp.writer.print "<li>${it.product.description} [<a href=\"addToCart?id=${it.id}\">add to cart</a>]</li>" }
			resp.writer.print "</body></html>"
		}
	}

	protected void addToCart(req, resp) {
		def id = req.getParameter('id')
		if (!id) { resp.sendRedirect("/"); return; }
		Cart.withTransaction {
			def cart = getCart(req)
			def item = Item.get(id)
			if (!cart.items.find { it.item.id == item.id }) {
				cart.items.add( new CartItem(item:item, quantity:1, inStock:true, total:item.listPrice) )
			}
			resp.sendRedirect("/viewCart")
		}
	}

	protected void removeFromCart(req, resp) {
		def id = req.getParameter('id')
		if (!id) { resp.sendRedirect("/"); return; }
		Cart.withTransaction {
			def cart = getCart(req)
			def item = cart.items.find { it.item.id = id }
			println "cart=${cart.dump()}"
			cart.removeFromItems(item)
			println "cart=${cart.dump()}"
			resp.sendRedirect("/viewCart")
		}
	}
	
	protected void viewCart(req, resp) {
		Cart.withTransaction { 
			def cart = getCart(req)
			resp.writer.print "<html><body>${header}"
			resp.writer.print "<h1>Cart</h1><ul>"
			cart.items.each {
				println "it=${it.dump()}"
				if (!it.item.isAttached()) { it.item.attach() }
				resp.writer.print "<li>${it.item.product.name} - quantity=${it.quantity} [<a href=\"removeFromCart?id=${it.item.id}\">remove from cart</a>]</li>" 
			}
			resp.writer.print "</ul><a href=\"/submitCart\">Submit Cart</a></body></html>"
		}
	}
	
	protected void submitCart(req, resp) {
		Cart.withTransaction { 
			def cart = getCart(req)
			resp.writer.print "<html><body>${header}"
			resp.writer.print "<h1>Cart Submitted</h1>"
			resp.writer.print "</body></html>"
			req.getSession().setAttribute("cart", null)
		}
	}
	
	def getCart(req) {		
		def cart = req.getSession().getAttribute("cart")
		if (!cart) {
			cart = new Cart(items:[])
			req.getSession().setAttribute("cart", cart)
		}
		return cart
	}

}

// Run the jetty server
try {
	def port = (args.size() > 0) ? Integer.parseInt(args[0]) : 8080	
	runServer(port)
} catch (Throwable t) {
	t.printStackTrace()
}
